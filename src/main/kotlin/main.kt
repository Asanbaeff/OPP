fun main() {

    val post = Post(text = " Старый пост ")
    val updatedPost = post.copy(text = " Новый пост ")

    val result1 = WallService.add(post)
    val result2 = WallService.update(updatedPost)

    println("$result1 , $result2")
    println(WallService.getAllPosts())

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
    val likes: Likes? = null          // Объект лайков (вложенный класс)
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
    private var posts = emptyArray<Post>() // Массив для хранения постов

    fun add(post: Post): Post {
        posts += post
        return posts.last()
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
        posts = emptyArray()
    }

}