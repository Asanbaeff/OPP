fun main() {

    val post = Post(text = " Старый пост ")
    val updatedPost = post.copy(text = " Новый пост ")

    val result1 = WallService.add(post)
    val result2 = WallService.update(updatedPost)

    println(WallService.getAllPosts())

    val postRep = PostRep(id = 1, content = "Репост абстрактный класс")

    val audioAttachment = AudioAttachment()
    val videoAttachment = VideoAttachment()
    val photoAttachment = PhotoAttachment()
    val docAttachment = DocAttachment()
    val linkAttachment = LinkAttachment()

    postRep.addAttachment(audioAttachment)
    postRep.addAttachment(videoAttachment)
    postRep.addAttachment(photoAttachment)
    postRep.addAttachment(docAttachment)
    postRep.addAttachment(linkAttachment)

    println("RePost ID: ${postRep.id}, Content: ${postRep.content}")
    for (attachment in postRep.attachments) {
        println("Attachment Type: ${attachment.type}")
    }

    val postInf = PostInf(idInf = 101, contentInf = "Репост второй интерфейс")
    val audioInf = AudioInf(duration = 180, bitrate = 320)
    val audioAttachmentInf = AudioAttachmentInf(audioInf)
    val videoInf = VideoInf(duration = 300, resolution = "1920x1080")
    val videoAttachmentInf = VideoAttachmentInf(videoInf)
    val photoInf = PhotoInf(width = 800, height = 600)
    val photoAttachmentInf = PhotoAttachmentInf(photoInf)

    postInf.addAttachmentInf(audioAttachmentInf)
    postInf.addAttachmentInf(videoAttachmentInf)
    postInf.addAttachmentInf(photoAttachmentInf)

    println("PostInf ID: ${postInf.idInf}, ContentInf: ${postInf.contentInf}")
    postInf.showAttachmentsInf()


}

data class Post(
    var id: Int = 0,         // Уникальный идентификатор поста
    val ownerId: Int = 0,    // Идентификатор владельца поста
    val fromId: Int = 0,     // Идентификатор отправителя (может отличаться от владельца)
    val createdBy: Int = 0,  // Идентификатор создателя (опциональное поле)
    val date: Long = 0L,     // Время публикации поста
    val text: String = "",   // Текст поста
    val replyOwnerId: Int? = null,  // Идентификатор владельца комментария (если пост является комментарием)
    val replyPostId: Int? = null,   // Идентификатор родительского поста (если пост является комментарием)
    val friendsOnly: Boolean = false,  // Видимость поста только друзьям
    val comments: Comments? = null,   // Объект комментариев (вложенный класс)
    val likes: Likes? = null,         // Объект лайков (вложенный класс)
    val attachments: List<Attachment> = emptyList() // Массив вложений
)


data class Comments(
    val count: Int = 0,      // Количество комментариев
    val canPost: Boolean = false  // Возможность комментирования
)

data class Likes(
    val count: Int = 0,      // Количество лайков
    val userLikes: Boolean = false,  // Лайкнул ли текущий пользователь
    val canLike: Boolean = false    // Возможность поставить лайк
)

object WallService {
    private var posts = mutableListOf<Post>() // Массив для хранения постов
    private var lastId = 0

    fun add(post: Post): Post {
        val newPost = post.copy(id = ++lastId)
        posts.add(newPost)
        return newPost
    }

    fun update(post: Post): Boolean {
        val index = posts.indexOfFirst { it.id == post.id }
        if (index != -1) {
            posts[index] = post.copy()
            return true
        }
        return false
    }

    fun getAllPosts(): List<Post> {
        return posts.toList()
    }

    fun clear() {
        posts.clear()
        lastId = 0
    }
}

abstract class Attachment(val type: String) //абстрактный класс

class AudioAttachment : Attachment(type = "аудио")
class VideoAttachment : Attachment(type = "видео")
class PhotoAttachment : Attachment(type = "фото")
class DocAttachment : Attachment(type = "документ")
class LinkAttachment : Attachment(type = "ссылка")

class PostRep(val id: Int, val content: String) {
    val attachments = mutableListOf<Attachment>()

    fun addAttachment(attachment: Attachment) {
        attachments.add(attachment)
    }
}

// Интерфейс AttachmentInf
interface AttachmentInf {
    val type: String
}

data class AudioInf(val duration: Int, val bitrate: Int)
data class VideoInf(val duration: Int, val resolution: String)
data class PhotoInf(val width: Int, val height: Int)

class AudioAttachmentInf(val audioInf: AudioInf) : AttachmentInf {
    override val type: String = "аудио"
}

class VideoAttachmentInf(val videoInf: VideoInf) : AttachmentInf {
    override val type: String = "видео"
}

class PhotoAttachmentInf(val photoInf: PhotoInf) : AttachmentInf {
    override val type: String = "фото"
}

class PostInf(val idInf: Int, val contentInf: String) {
    private val attachmentsInf = mutableListOf<AttachmentInf>()

    fun addAttachmentInf(attachmentInf: AttachmentInf) {
        attachmentsInf.add(attachmentInf)
    }

    fun showAttachmentsInf() {
        for (attachmentInf in attachmentsInf) {
            println("AttachmentInf Type: ${attachmentInf.type}")
        }
    }
}




