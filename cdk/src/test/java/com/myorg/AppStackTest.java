package com.myorg;

import com.example.AppStack;
import software.amazon.awscdk.App;
import software.amazon.awscdk.assertions.Template;
import java.io.IOException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppStackTest {

    @Test
    public void testStack() throws IOException {
        App app = new App();
        AppStack stack = new AppStack(app, "test");
        Template template = Template.fromStack(stack);
        assertTrue(true);
    }
}
