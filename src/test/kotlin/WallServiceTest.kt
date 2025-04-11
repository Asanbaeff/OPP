import org.junit.Test
import org.junit.Before
import org.junit.Assert.*

class WallServiceTest {

    @Test
    fun addTest() {
        val newPost = Post(1)
        val result = WallService.add(newPost)
        assertNotEquals(0, result.id)
    }

    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun testUpdateExistingPost() {
        val originalPost = Post(id = 1, text = "Original Title")
        WallService.add(originalPost)
        val updatedPost = originalPost.copy(text = "Updated Title")
        val result = WallService.update(updatedPost)
        assertEquals(true, result)
        val actualPost = WallService.getAllPosts().firstOrNull { it.id == 1 }
        assertEquals("Updated Title", actualPost?.text)
    }

    @Test
    fun testUpdateNonExistingPost() {
        val nonExistingPost = Post(id = 9999, text = "Non-existent Post")
        val result = WallService.update(nonExistingPost)
        assertEquals(false, result)
    }


    @Test
    fun shouldAddCommentToExistingPost() {
        val post = Post(id = 1, text = "Hello")
        WallService.add(post)

        val comment = Comments(id = 0, postId = 1, comment = "Nice post!")
        val addedComment = WallService.createComment(postId = 1, comment = comment)

        assertEquals(1, addedComment.id) // Проверяем ID комментария
        assertEquals(1, addedComment.postId) // Проверяем ID поста
        assertEquals("Nice post!", addedComment.comment) // Проверяем содержимое комментария
    }

    @Test(expected = PostNotFoundException::class)
    fun shouldThrowWhenPostNotFound() {
        val comment = Comments(id = 0, postId = 99, comment = "This post does not exist")
        WallService.createComment(postId = 99, comment = comment)
    }

}