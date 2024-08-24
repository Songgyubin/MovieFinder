import com.gyub.core.ui.util.isDateTodayOrBefore
import org.junit.Test
import kotlin.test.assertEquals

class UiUtilTest {

    @Test
    fun `영화 배포 날짜가 오늘 날짜 이전인지 판단`() {
        val releaseDate = "1994-10-11"

        assertEquals(
            expected = true,
            actual = isDateTodayOrBefore(releaseDate)
        )
    }
}