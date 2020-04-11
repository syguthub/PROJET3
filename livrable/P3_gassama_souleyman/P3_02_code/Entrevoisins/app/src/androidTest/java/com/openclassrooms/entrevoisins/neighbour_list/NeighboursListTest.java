
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Random;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(allOf(withId(R.id.list_neighbours),childAtPosition(withId(R.id.container),0)))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        onView(allOf(withId(R.id.list_neighbours),childAtPosition(withId(R.id.container),0))).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
       onView(allOf(withId(R.id.list_neighbours),childAtPosition(withId(R.id.container),0)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
//        // Then : the number of element is 11
        onView(allOf(withId(R.id.list_neighbours),childAtPosition(withId(R.id.container),0))).check(withItemCount(ITEMS_COUNT-1));
    }


    @Test
    public void listNeighbourActivityTest() {

        onView(allOf(withId(R.id.list_neighbours),childAtPosition(withId(R.id.container),0)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(11,click()));

        onView(withId(R.id.detail_voisin)).check(matches(isDisplayed()));

//
    }
    @Test
    public void verifierLesNonDuProfilSecection(){
        int position =9;

        onView(allOf(withId(R.id.list_neighbours),childAtPosition(withId(R.id.container),0)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(position,click()));

        onView(withId(R.id.detail_voisin)).check(matches(isDisplayed()));

        Neighbour neighbour= DI.getNeighbourApiService().getNeighbours().get(position);
        String nomNeighbour= neighbour.getName();

        onView(withId(R.id.nomProfil)).check(matches(withText(nomNeighbour)));
        pressBack();
    }

    @Test
    public void dansFavorisQueDesFavoris(){
        int positionOngletFavoris =1;

        int nbrF = chargeFavoiris();
// changer d'onglé de neighbour à favoris
        onView( allOf(withContentDescription("Favorites"), childAtPosition(childAtPosition(withId(R.id.tabs), 0), positionOngletFavoris)))

                .perform(click());

//verifier si dans longlé favoris il y a bien que des favoris
        for (int j= 0; j<nbrF; j++) {


            onView(allOf(withId(R.id.list_neighbours),childAtPosition(withId (R.id.container),positionOngletFavoris)))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(j,click()));

            onView(withId(R.id.detail_voisin)).check(matches(isDisplayed()));

            onView(withId(R.id.boutonFavoris)).check(matches(withContentDescription("favoris")));
            pressBack();
        }
//
//

    }

    public int chargeFavoiris(){
        List<Neighbour> neighbours=DI.getNeighbourApiService().getNeighbours();

        Random random=new Random();

        int nbrF= random.nextInt(neighbours.size());

        for(int i = 0; i<nbrF; i++){

            onView(allOf(withId(R.id.list_neighbours),childAtPosition(withId(R.id.container),0)))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(i,click()));

            onView(withId(R.id.detail_voisin)).check(matches(isDisplayed()));

            onView(withId(R.id.boutonFavoris)).perform(click());

            pressBack();
        }
        return nbrF;
    }
    public static Matcher<View> childAtPosition(
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