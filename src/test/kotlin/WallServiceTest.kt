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
}