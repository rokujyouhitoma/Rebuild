package rejasupotaro.rebuild.test.utils;

import android.test.AndroidTestCase;

import java.util.List;

import rejasupotaro.rebuild.utils.StringUtils;

public class StringUtilsTest extends AndroidTestCase {

    public void testCapitalize() {
        String source = "abc";
        assertEquals("Abc", StringUtils.capitalize(source));
    }

    public void testRemoveNewLines() {
        {
            String source = "Hello World!";
            assertEquals("Hello World!", StringUtils.removeNewLines(source));
        }
        {
            String source = "Hello World!\n";
            assertEquals("Hello World!", StringUtils.removeNewLines(source));
        }
        {
            String source = "Hello\nWorld!\n";
            assertEquals("HelloWorld!", StringUtils.removeNewLines(source));
        }
    }

    public void testGetGuestNames() {
        {
            List<String> guestNameList = StringUtils.getGuestNames("");
            assertNotNull(guestNameList);
            assertTrue(guestNameList.isEmpty());
        }
        {
            List<String> guestNameList = StringUtils.getGuestNames("xxxxx");
            assertNotNull(guestNameList);
            assertTrue(guestNameList.isEmpty());
        }
        {
            List<String> guestNameList = StringUtils.getGuestNames("舘野祐一さん (@hotchpotch) をゲストに迎えて、Podcast クライアント、モバイルアプリ開発、TestFlight, WhatsApp, iOS セキュリティなどについて話しました。");
            assertNotNull(guestNameList);
            assertEquals(1, guestNameList.size());
        }
        {
            List<String> guestNameList = StringUtils.getGuestNames("adamrocker さん (@adamrocker)、 Motohiro Takayamaさん (@mootoh)をゲストに迎えて、Nexus 5, Android 4.4 などについて話しました。");
            assertNotNull(guestNameList);
            assertEquals(2, guestNameList.size());
        }
    }

    public void testBuildTwitterLinkText() {
        {
            String source = null;
            assertEquals("", StringUtils.buildTwitterLinkText(source));
        }
        {
            String source = "";
            assertEquals("", StringUtils.buildTwitterLinkText(source));
        }
        {
            String source = "Hello World";
            assertEquals("", StringUtils.buildTwitterLinkText(source));
        }
        {
            String source = "Hello (World)";
            assertEquals("", StringUtils.buildTwitterLinkText(source));
        }
        {
            String source = "rejasupotaro@gmail.com";
            assertEquals("rejasupotarogmail.com", StringUtils.buildTwitterLinkText(source));
        }
        {
            String source = "伊藤直也さん(@naoya_ito)をゲストに迎えてポッドキャスト、LTSV、RubyMotion、Perlなどについて話しました。";
            assertEquals(
                    "伊藤直也さん(<a href=\"https://twitter.com/naoya_ito\">@naoya_ito</a>)をゲストに迎えてポッドキャスト、LTSV、RubyMotion、Perlなどについて話しました。",
                    StringUtils.buildTwitterLinkText(source));
        }
        {
            String source = "伊藤直也さん (@naoya_ito)、宮下剛輔さん (@gosukenator) をゲストに迎えて、Immutable Infrastructure, Docker, Packer, Serf などについて話しました。";
            assertEquals(
                    "伊藤直也さん (<a href=\"https://twitter.com/naoya_ito\">@naoya_ito</a>)、宮下剛輔さん (<a href=\"https://twitter.com/gosukenator\">@gosukenator</a>) をゲストに迎えて、Immutable Infrastructure, Docker, Packer, Serf などについて話しました。",
                    StringUtils.buildTwitterLinkText(source));
        }
    }
}
