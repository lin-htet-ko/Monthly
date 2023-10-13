package com.linhtetko.monthly.data.network

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.linhtetko.monthly.data.local.pref.PreferenceManager
import com.linhtetko.monthly.data.model.DashboardModel
import com.linhtetko.monthly.data.model.ItemModel
import com.linhtetko.monthly.data.model.UserModel
import com.linhtetko.monthly.ui.screens.Screen
import java.io.File
import javax.inject.Inject

class FirebaseApiConsumer @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage,
    private val preferenceManager: PreferenceManager
) : ApiConsumer {

    companion object {

        const val COLLECTION_USERS = "users"
        const val COLLECTION_ITEMS = "items"

        const val DOC_FIELD_UID = "userId"
        const val DOC_FIELD_TYPE = "type"
        const val DOC_FIELD_PRICE = "price"

        const val GENERAL_ERROR_MESSAGE = "Something Wrong"
    }

    private inline fun <reified T> DocumentReference.handleState(
        state: ApiConsumer.State<T>,
    ) {
        state.onLoading()
        addSnapshotListener { value, error ->
            if (value != null) {
                val item = value.toObject(T::class.java)
                if (item != null)
                    state.onSuccess(item)
            }
            if (error != null) {
                state.onFailure(error.localizedMessage ?: GENERAL_ERROR_MESSAGE)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private inline fun <reified T, E> Task<T>.handleState(
        state: ApiConsumer.State<E>,
    ) {
        state.onLoading()
        addOnSuccessListener {
            when (it) {
                is AuthResult -> {
                    preferenceManager.putString(PreferenceManager.KEY_UID, it.user?.uid ?: "")
                    state.onSuccess(true as E)
                }

                is DashboardModel -> {
                    state.onSuccess(it as E)
                }

                else -> {
                    state.onSuccess(true as E)
                }
            }
        }
        addOnFailureListener {
            state.onFailure(it.localizedMessage ?: GENERAL_ERROR_MESSAGE)
        }
    }

    private inline fun <reified T> Query.handleState(
        state: ApiConsumer.State<T>,
        clazz: Class<*>? = null
    ) {
        state.onLoading()
        addSnapshotListener { value, error ->
            if (value != null) {
                val docs = value.documents
                if (clazz != null) {
                    when (clazz) {
                        UserModel::class.java -> {
                            val items =
                                docs.mapNotNull { doc -> doc.toObject(UserModel::class.java) }
                            state.onSuccess(items as T)
                        }

                        ItemModel::class.java -> {
                            val items =
                                docs.mapNotNull { doc -> doc.toObject(ItemModel::class.java) }
                            state.onSuccess(items as T)
                        }
                    }
                }
            }
            if (error != null) {
                state.onFailure(error.localizedMessage ?: GENERAL_ERROR_MESSAGE)
            }
        }
    }

    override fun getProfile(id: String, state: ApiConsumer.State<UserModel>) {
        firestore.collection(COLLECTION_USERS)
            .document(id)
            .handleState(state)
    }

    override fun getFlatmates(state: ApiConsumer.State<List<UserModel>>) {
        firestore.collection(COLLECTION_USERS)
            .handleState(state, UserModel::class.java)
    }

    override fun getItems(state: ApiConsumer.State<List<ItemModel>>) {
        firestore.collection(COLLECTION_ITEMS)
            .whereEqualTo(DOC_FIELD_TYPE, Screen.CreateItem.PATH_ITEM)
            .handleState(state, ItemModel::class.java)
    }

    override fun getGeneralItems(state: ApiConsumer.State<List<ItemModel>>) {
        firestore.collection(COLLECTION_ITEMS)
            .whereEqualTo(DOC_FIELD_TYPE, Screen.CreateItem.PATH_GENERAL)
            .handleState(state, ItemModel::class.java)
    }

    override fun getUserItems(uid: String, state: ApiConsumer.State<List<ItemModel>>) {
        firestore.collection(COLLECTION_ITEMS)
            .whereEqualTo(DOC_FIELD_UID, uid)
            .handleState(state, ItemModel::class.java)
    }

    override fun getDashboard(state: ApiConsumer.State<DashboardModel>) {
        firestore.collection(COLLECTION_ITEMS)
            .get()
            .continueWithTask { query ->
                firestore.collection(COLLECTION_USERS).get()
                    .continueWith {
                        val userSize = it.result.documents.size
                        val total = query.result.mapNotNull { doc -> doc.getLong(DOC_FIELD_PRICE) }
                            .sumOf { doc -> doc }

                        DashboardModel(total = total, each = (total / userSize.toDouble()))
                    }
            }.handleState(state)
    }

    override fun postItem(path: String, item: ItemModel, state: ApiConsumer.State<Boolean>) {
        val time = System.currentTimeMillis()
        firestore.collection(COLLECTION_ITEMS)
            .document(time.toString())
            .set(item.copy(type = path))
            .handleState(state)
    }

    override fun postFlatmate(user: UserModel, state: ApiConsumer.State<Boolean>) {
        if (!user.id.isNullOrEmpty())
            firestore.collection(COLLECTION_USERS)
                .document(user.id!!)
                .set(user)
                .handleState(state)
    }

    override fun register(user: UserModel, image: File, state: ApiConsumer.State<Boolean>) {
        if (!user.email.isNullOrEmpty() && !user.password.isNullOrEmpty()) {
            state.onLoading()
            auth.createUserWithEmailAndPassword(user.email, user.password)
                .continueWithTask { authResult ->
                    user.id = authResult.result.user?.uid
                    if (!authResult.result.user?.uid.isNullOrEmpty()) {
                        preferenceManager.putString(
                            PreferenceManager.KEY_UID,
                            authResult.result.user?.uid!!
                        )
                    }
                    val ref = storage.getReference("$COLLECTION_USERS/${user.id}.jpg")
                    ref.putBytes(image.readBytes()).continueWithTask {
                        user.id = authResult.result.user?.uid
                        ref.downloadUrl
                    }.continueWith {
                        user.imgPath = it.result.toString()
                        postFlatmate(user, state)
                    }
                }.handleState(state)
        } else
            state.onFailure("Email or Password is required")
    }

    override fun login(email: String, password: String, state: ApiConsumer.State<Boolean>) {
        auth.signInWithEmailAndPassword(email, password)
            .handleState(state)
    }

    override fun logout() {
        auth.signOut()
        preferenceManager.clear()
    }
}