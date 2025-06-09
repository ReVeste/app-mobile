package com.example.app_mobile.presentation.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.app_mobile.data.network.api.CriarContaRequest
import com.example.app_mobile.data.network.api.FeedbackApiService
import com.example.app_mobile.data.network.api.PedidoApiService
import com.example.app_mobile.domain.model.CarrinhoDto
import com.example.app_mobile.domain.model.Feedback
import com.example.app_mobile.domain.model.FeedbackRequisicaoDto
import com.example.app_mobile.domain.model.PedidoDto
import com.example.app_mobile.domain.model.Produto
import com.example.app_mobile.domain.model.SessaoUsuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class TelaAvaliarViewModel(
    val apiFeedback : FeedbackApiService,
    val apiPedido : PedidoApiService,
    val usuarioLogado: SessaoUsuario,
): ViewModel() {

    var pedido by mutableStateOf<CarrinhoDto?>(null)
        private set

    var carregando by mutableStateOf(false)
        private set

    var avaliado by mutableStateOf(false)
        private set

    private val _erro = MutableStateFlow<String?>(null)
    val erro: StateFlow<String?> = _erro

    var mensagemSucesso by mutableStateOf<String?>(null)

    fun buscarPedidoPorId(id: Int) {
        viewModelScope.launch {
            carregando = true
            try {
                pedido = apiPedido.buscarCarrinho(id)
            } catch (e: Exception) {
                Log.e("API", "Erro ao buscar pedido por ID: ${e.message}")
                _erro.value = "Erro ao buscar pedido"
            } finally {
                carregando = false
            }
        }
    }

    // criar feedback

    private suspend fun uploadImageToCloudinary(imageUri: Uri): String {
        return suspendCancellableCoroutine { continuation ->
            MediaManager.get().upload(imageUri)
                // VOCÊ DEVE USAR SEU UPLOAD PRESET UNSIGNED AQUI!
                // Crie um em seu dashboard do Cloudinary: Settings -> Upload -> Add upload preset
                .unsigned("SEU_UPLOAD_PRESET_UNSIGNED") // <-- MUDAR AQUI!
                // .option("folder", "avaliacoes") // Opcional: para organizar no Cloudinary
                .callback(object : UploadCallback {
                    override fun onStart(requestId: String) {
                        Log.d("Cloudinary", "Upload started: $requestId")
                    }

                    override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {
                        val progress = (bytes * 100 / totalBytes).toInt()
                        Log.d("Cloudinary", "Progress: $progress%")
                    }

                    override fun onSuccess(requestId: String, resultData: Map<*, *>?) {
                        val url = resultData?.get("url") as? String
                        if (url != null) {
                            continuation.resume(url) // Retorna a URL em caso de sucesso
                        } else {
                            continuation.resumeWithException(
                                IllegalStateException("Cloudinary upload successful, but URL not found.")
                            )
                        }
                    }

                    override fun onError(requestId: String, error: ErrorInfo) {
                        Log.e("Cloudinary", "Upload error: ${error.description}")
                        continuation.resumeWithException(
                            RuntimeException("Cloudinary upload failed: ${error.description}")
                        )
                    }

                    override fun onReschedule(requestId: String, error: ErrorInfo) {
                        Log.w("Cloudinary", "Upload rescheduled: ${error.description}")
                    }
                })
                .dispatch() // Inicia o upload

            // Se a coroutine for cancelada, você pode tentar cancelar o upload do Cloudinary aqui
            continuation.invokeOnCancellation {
                // MediaManager.get().cancelRequest(requestId) // Você precisaria armazenar o requestId para cancelar
            }
        }
    }

    fun criarAvaliacao(comentario: String, pedidoId: Int, usuarioId: Int?, imagens: SnapshotStateList<Uri?>) {
        viewModelScope.launch {
            _erro.value = null
            carregando = true

            try {
                val uploadedImageUrls = mutableListOf<String>()

                // Fazer o upload de cada imagem para o Cloudinary SEQUENCIALMENTE
                for (imageUri in imagens) {
                    if (imageUri != null) {
                        try {
                            val url = uploadImageToCloudinary(imageUri) // Chama a função de upload
                            uploadedImageUrls.add(url)
                        } catch (e: Exception) {
                            Log.e("TelaAvaliarVM", "Erro ao fazer upload da imagem: ${e.message}")
                            _erro.value = "Erro ao fazer upload de uma imagem: ${e.message}. Avaliação não enviada."
                            carregando = false // Desativa o carregamento em caso de falha no upload
                            return@launch // Sai da coroutine se o upload de uma imagem falhar
                        }
                    }
                }

                val dto = FeedbackRequisicaoDto(
                    comentario = comentario,
                    pedido = pedidoId,
                    usuario = usuarioId,
                    imagensFeedback = uploadedImageUrls
                )
                apiFeedback.criarAvaliacao(dto)

                mensagemSucesso = "Obrigado por avaliar!"
                carregando = false
            } catch (e: Exception) {
                Log.e("API", "Erro ao criar conta: ${e.message}")
                _erro.value = "Erro ao criar conta: ${e.message}"
            } finally {
                avaliado = true
            }
        }
    }
}