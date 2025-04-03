fun main() {

    val post = Post(text = "Старый пост")
    val updatedPost = post.copy(text = "Новый пост")

    val result1 = WallService.add(post)
    val result2 = WallService.update(updatedPost)
    println(WallService.getAllPosts())

    val postRep = PostRep(id = 1, content = "Репост абстрактный класс")
    val audioAttachment =
        AudioAttachment(audio = Audio(audioUrl = "http://audio.mp3", duration = 120, title = "Аудио"))
    val videoAttachment =
        VideoAttachment(video = Video(videoUrl = "http://video.mp4", duration = 300, title = "Видео"))
    val photoAttachment = PhotoAttachment(photo = Photo(photoUrl = "http://photo.jpg", width = 800, height = 600))
    val docAttachment = DocAttachment(doc = Doc(documentUrl = "http://doc.pdf", title = "Документ", sizeInBytes = 2048))
    val linkAttachment =
        LinkAttachment(link = Link(documentUrl = "http://link", title = "Ссылка", url = "http://example.com"))

    postRep.addAttachment(audioAttachment)
    postRep.addAttachment(videoAttachment)
    postRep.addAttachment(photoAttachment)
    postRep.addAttachment(docAttachment)
    postRep.addAttachment(linkAttachment)

    println("RePost ID: ${postRep.id}, Content: ${postRep.content}")
    for (attachment in postRep.attachments) {
        println("Attachment Type: ${attachment.type}")
    }
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

abstract class Attachment(open val type: String)

data class PhotoAttachment(
    override val type: String = "фото",
    val photo: Photo
) : Attachment(type)

data class Photo(
    val photoUrl: String,
    val width: Int,
    val height: Int
)

data class VideoAttachment(
    override val type: String = "видео",
    val video: Video,
) : Attachment(type)

data class Video(
    val videoUrl: String,
    val duration: Int,
    val title: String
)

data class AudioAttachment(
    override val type: String = "аудио",
    val audio: Audio,
) : Attachment(type)

data class Audio(
    val audioUrl: String,
    val duration: Int,
    val title: String
)

data class DocAttachment(
    override val type: String = "документ",
    val doc: Doc,
) : Attachment(type)

data class Doc(
    val documentUrl: String,
    val title: String,
    val sizeInBytes: Long
)

data class LinkAttachment(
    override val type: String = "ссылка",
    val link: Link,
) : Attachment(type)

data class Link(
    val documentUrl: String,
    val title: String,
    val url: String
)

class PostRep(val id: Int, val content: String) {
    val attachments = mutableListOf<Attachment>()

    fun addAttachment(attachment: Attachment) {
        attachments.add(attachment)
    }
}











