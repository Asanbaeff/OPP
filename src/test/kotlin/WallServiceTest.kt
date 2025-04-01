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
    fun updateExistingTrue() {
        val originalPost = Post(id = 1, text = "Original Title")
        WallService.update(originalPost)
        val updatedPost = Post(id = 1, text = "Updated Title")
        val result = WallService.update(updatedPost)
        assertEquals(false, result)

    }

    @Test
    fun updateExistingFalse() {
        val originalPost = Post(id = 1, text = "Original Title")
        WallService.update(originalPost)
        val nonExistingPost = Post(id = 9999, text = "Non-existent Post")
        val result = WallService.update(nonExistingPost)
        assertEquals(false, result)

    }
}