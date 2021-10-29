package test;

import com.shui.dao.UserDao;
import com.shui.domain.entity.User;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


class MockitoTest {

//    junit4，测试类加上 @RunWith(MockitoJUnitRunner.class) 注解
//    @Rule
//    public MockitoRule mockitoRule = MockitoJUnit.rule();

    //    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    @Mock
    private UserDao userDao;

    @BeforeAll
    static void beforeAll() {
        System.out.println("MockitoTest all test start");
    }

    @BeforeEach
    void setUp() {
        //        Mockito mockito = new Mockito();
//        userDao = mockito.mock(UserDao.class);

        //  @Mock
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("first")
    void test1() {
        Mockito mockito = new Mockito();
        User mockUser = mockito.mock(User.class);

        mockUser.setId(1L);
        mockito.verify(mockUser).setId(1L);
//        mockito.verify(mockUser).setName("123");
    }

    @Disabled("skip test2")
    @Test()
    void test2() {
        Mockito mockito = new Mockito();
        User mockUser = mockito.mock(User.class);

        mockUser.setId(1L);
        when(mockUser.getId()).thenReturn(1L);
        when(mockUser.getName()).thenThrow(NoSuchFieldException.class);
    }

    @Test
    void test3() {
        Mockito mockito = new Mockito();
        User mockUser = mockito.mock(User.class);

        mockUser.setId(1L);
        mockUser.setId(1L);
        mockito.verify(mockUser, times(2)).setId(1L);
    }

    @Test
    void test4() {
        User expectedUser = new User();
        expectedUser.setId(1L);
        expectedUser.setName("test4");

        when(userDao.selectById(1)).thenReturn(expectedUser);
        assertThat(userDao.selectById(1).getId()).isEqualTo(1);

        // 直接操作，是真实的，会一直在实例上
        User realUser = spy(new User());
        realUser.setId(2L);

        assertThat(realUser.getId()).isEqualTo(2);
        // stubbing 是mock的，可理解为jdk动态代理，预设，增强
        when(realUser.getName()).thenReturn("realUser");
        assertThat(realUser.getName()).isEqualTo("realUser");
        // 真实在stubbing之后，真实无效；
        //              之前，真实无效
        realUser.setName("testRealUserName");
        assertThat(realUser.getName()).isEqualTo("realUser");
    }

    @Test
    void test5() {
        //    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
        User user = userDao.selectById(1);
        when(user.getId()).thenReturn(1L);
        assertThat(user.getId()).isEqualTo(1);
    }

    @AfterAll
    static void afterAll() {
        System.out.println("MockitoTest all test done");
    }
}
