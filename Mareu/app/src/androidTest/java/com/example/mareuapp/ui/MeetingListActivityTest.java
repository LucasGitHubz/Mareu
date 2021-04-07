package com.example.mareuapp.ui;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.mareu.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MeetingListActivityTest {

    @Rule
    public ActivityTestRule<MeetingListActivity> mActivityTestRule = new ActivityTestRule<>(MeetingListActivity.class);

    @Test
    public void createMeeting() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.add_meeting_button),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.room_lyt),
                        childAtPosition(
                                allOf(withId(R.id.textInpoutContainer),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                0)));
        appCompatEditText.perform(scrollTo(), replaceText("test"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.topic_lyt),
                        childAtPosition(
                                allOf(withId(R.id.textInpoutContainer),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                1)));
        appCompatEditText2.perform(scrollTo(), replaceText("test"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.participants_lyt),
                        childAtPosition(
                                allOf(withId(R.id.textInpoutContainer),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                2)));
        appCompatEditText3.perform(scrollTo(), replaceText("test@gmail.com"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.add_button), withText("Ajouter"),
                        childAtPosition(
                                allOf(withId(R.id.textInpoutContainer),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                8)));
        materialButton.perform(scrollTo(), click());

        ViewInteraction viewGroup = onView(
                allOf(withParent(allOf(withId(R.id.activity_list_user_rv),
                        withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class)))),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));
    }

    @Test
    public void deleteMeeting() {
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.add_meeting_button),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.room_lyt),
                        childAtPosition(
                                allOf(withId(R.id.textInpoutContainer),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                0)));
        appCompatEditText.perform(scrollTo(), replaceText("dsq"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.topic_lyt),
                        childAtPosition(
                                allOf(withId(R.id.textInpoutContainer),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                1)));
        appCompatEditText2.perform(scrollTo(), replaceText("dsq"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.participants_lyt),
                        childAtPosition(
                                allOf(withId(R.id.textInpoutContainer),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                2)));
        appCompatEditText3.perform(scrollTo(), replaceText("dq"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.add_button), withText("Ajouter"),
                        childAtPosition(
                                allOf(withId(R.id.textInpoutContainer),
                                        childAtPosition(
                                                withClassName(is("android.widget.ScrollView")),
                                                0)),
                                8)));
        materialButton.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.item_list_meeting_delete_button),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.activity_list_user_rv),
                                        0),
                                3),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.noMeetingTV), withText("Aucune réunion créée"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView.check(matches(withText("Aucune réunion créée")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
