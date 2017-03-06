package com.stephnoutsa.cuib;

import android.content.Context;

import com.stephnoutsa.cuib.adapters.LecturerAdapter;
import com.stephnoutsa.cuib.models.Lecturer;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by stephnoutsa on 11/29/16.
 */

@RunWith(MockitoJUnitRunner.class)
public class Test {

    @Mock
    Context mockContext;

    @org.junit.Test
    public void textTrimmer() {
        String expected = "This is just a random text that I want to trim out...";

        List<Lecturer> lecturers = new ArrayList<>();
        LecturerAdapter adapter = new LecturerAdapter(mockContext, lecturers);
        String trimmed = adapter.trimText("This is just a random text that I want to trim out using my adapter\'s method");

        assertThat(trimmed, is(expected));
    }

}
