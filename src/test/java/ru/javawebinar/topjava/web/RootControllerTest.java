package ru.javawebinar.topjava.web;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.to.UserTo;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.TestUtil.userAuth;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.USER;

class RootControllerTest extends AbstractControllerTest {

    @Test
    void testUsers() throws Exception {
        mockMvc.perform(get("/users")
                .with(userAuth(ADMIN)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"));
    }

    @Test
    void testUnAuth() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    void testMeals() throws Exception {
        mockMvc.perform(get("/meals")
                .with(userAuth(USER)))
                .andDo(print())
                .andExpect(view().name("meals"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/meals.jsp"));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void testRegisterDuplicateEmail() throws Exception {
        UserTo created = new UserTo(null, "new", "admin@gmail.com", "qwerty", 2000);
        mockMvc.perform(post("/register")
                .param("name", created.getName())
                .param("email", created.getEmail())
                .param("password", created.getPassword())
                .param("caloriesPerDay", created.getCaloriesPerDay().toString())
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasErrors("userTo"))
                .andExpect(model().attribute("register", true))
                .andExpect(forwardedUrl("/WEB-INF/jsp/profile.jsp"));

    }
}