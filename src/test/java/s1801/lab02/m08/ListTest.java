package s1801.lab02.m08;

import j8spec.junit.J8SpecRunner;
import org.junit.runner.RunWith;

import static j8spec.J8Spec.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(J8SpecRunner.class)
public class ListTest {

    private List list;

    {
        describe("An empty list", () -> {

            beforeEach(() -> {
                list = new List();
            });

            it("starts empty", () -> {
                assertThat(list.isEmpty(), is(true));
            });

            it("has a null first node", () -> {
                assertThat(list.getFirst(), is(nullValue()));
            });

            it("has a null last node", () -> {
                assertThat(list.getLast(), is(nullValue()));
            });

            it("fails when removing from back", c -> c.expected(EmptyListException.class), () -> {
                list.removeFromBack();
            });

            it("fails when removing from front", c -> c.expected(EmptyListException.class), () -> {
                list.removeFromFront();
            });
        });

        describe("When inserting at front", () -> {

            beforeEach(() -> {
                list = new List();
                list.insertAtFront(1);
            });

            it("has first and last nodes pointing to the same reference on first insertion", () -> {
                assertThat(list.getFirst().getData(), is(1));
                assertThat(list.getLast().getData(), is(1));
            });

            it("pushes older elements to the end of the list on subsequent frontal insertions", () -> {
                list.insertAtFront(2);

                assertThat(list.getFirst().getData(), is(2));
                assertThat(list.getLast().getData(), is(1));
            });

            it("Chains nodes correctly on subsequent frontal insertions", () -> {
                list.insertAtFront(2);

                assertThat(list.getFirst().getNext().getData(), is(1));
            });
        });

        describe("When removing from front", () -> {
            beforeEach(() -> {
                list = new List();
                list.insertAtFront(1);
            });

            it("Removes the only element when a list has a single element", () -> {
                Object element = list.removeFromFront();

                assertThat(element, is(1));
                assertThat(list.isEmpty(), is(true));
            });

            it("pulls subsequent elements to the beginning of the list", () -> {
                list.insertAtFront(2);

                Object element = list.removeFromFront();

                assertThat(element, is(2));
                assertThat(list.getFirst().getData(), is(1));
                assertThat(list.getFirst().getNext(), is(nullValue()));
                assertThat(list.getLast().getData(), is(1));
            });
        });

        describe("When removing from back", () -> {
            beforeEach(() -> {
                list = new List();
                list.insertAtFront(1);
            });

            it("Removes the only element when a list has a single element", () -> {

                Object element = list.removeFromBack();

                assertThat(element, is(1));
                assertThat(list.isEmpty(), is(true));
            });

            it("Removes the last element and correctly preserves the remaining ones", () -> {
                list.insertAtFront(2);

                Object element = list.removeFromBack();

                assertThat(element, is(1));
                assertThat(list.getFirst().getData(), is(2));
                assertThat(list.getFirst().getNext(), is(nullValue()));
                assertThat(list.getLast().getData(), is(2));
            });
        });

        describe("When removing the first odd index", () -> {
            beforeEach(() -> {
                list = new List();
                list.insertAtFront(1);
                list.insertAtBack(2);
                list.insertAtBack(3);
            });

            it("Removes the first odd index when it is present", () -> {
                assertThat(list.remove_impar(), is(true));
                assertThat(list.getFirst().getData(), is(1));
                assertThat(list.getLast().getData(), is(3));
            });

            it("Does not remove the element when list is empty", () -> {
                List emptyList = new List();

                assertThat(emptyList.remove_impar(), is(false));
            });

            it("Does not remove the element when list has a single element", () -> {
                List listWithSingleElement = new List();
                listWithSingleElement.insertAtFront(1);

                assertThat(listWithSingleElement.remove_impar(), is(false));
            });

            it("Removes last element when the list has only two elements", () -> {
                List listWithTwoElements = new List();
                listWithTwoElements.insertAtFront(1);
                listWithTwoElements.insertAtBack(2);

                assertThat(listWithTwoElements.remove_impar(), is(true));
                assertThat(listWithTwoElements.getFirst().getData(), is(1));
                assertThat(listWithTwoElements.getLast().getData(), is(1));
            });

            it("Preserves correct node linking after removing the odd element", () -> {
                List list = new List();
                list.insertAtFront(1);
                list.insertAtBack(2);

                list.remove_impar();
                list.insertAtBack(3);

                assertThat(list.getFirst().getData(), is(1));
                assertThat(list.getLast().getData(), is(3));
            });
        });
    }

//            @Test
//            public void preserves_correct_linking_order_after_removing_from_back() {
//                list.insertAtFront(2);
//                list.insertAtFront(3);
//
//                Object element = list.removeFromFront();
//
//                assertThat(element, is(3));
//                assertThat(list.getFirst().getData(), is(2));
//                assertThat(list.getFirst().getNext().getData(), is(1));
//                assertThat(list.getLast().getData(), is(1));
//            }
//
//            @Test
//            public void preserves_correct_linking_order_after_removing_from_front() {
//                list.insertAtFront(2);
//                list.insertAtFront(3);
//
//                Object element = list.removeFromBack();
//
//                assertThat(element, is(1));
//                assertThat(list.getFirst().getData(), is(3));
//                assertThat(list.getFirst().getNext().getData(), is(2));
//                assertThat(list.getLast().getData(), is(2));
//            }
//        }
//
//        public static class WhenInsertingAtBack extends SetUp {
//
//            @Override
//            void configure() {
//                list.insertAtBack(1);
//            }
//
//            @Test
//            public void first_insertion_sets_both_first_and_last_as_same_node() {
//                assertThat(list.getFirst().getData(), is(1));
//                assertThat(list.getLast().getData(), is(1));
//            }
//
//            @Test
//            public void following_insertions_push_older_elements_to_the_head_of_the_list() {
//                list.insertAtBack(2);
//
//                assertThat(list.getFirst().getData(), is(1));
//                assertThat(list.getLast().getData(), is(2));
//            }
//
//            @Test
//            public void following_insertion_sets_the_next_node_of_the_first_node() {
//                list.insertAtBack(2);
//
//                assertThat(list.getFirst().getNext().getData(), is(2));
//            }
//
//            @Test
//            public void removeFromFront_removes_first_element() {
//                list.insertAtBack(2);
//
//                Object element = list.removeFromFront();
//
//                assertThat(element, is(1));
//                assertThat(list.getFirst().getData(), is(2));
//                assertThat(list.getFirst().getNext(), is(nullValue()));
//                assertThat(list.getLast().getData(), is(2));
//            }
//
//            @Test
//            public void removeFromBack_removes_last_element() {
//                list.insertAtBack(2);
//
//                Object element = list.removeFromBack();
//
//                assertThat(element, is(2));
//                assertThat(list.getFirst().getData(), is(1));
//                assertThat(list.getFirst().getNext(), is(nullValue()));
//                assertThat(list.getLast().getData(), is(1));
//            }
//
//            @Test
//            public void preserves_correct_linking_order_after_removing_from_back() {
//                list.insertAtBack(2);
//                list.insertAtBack(3);
//
//                Object element = list.removeFromFront();
//
//                assertThat(element, is(1));
//                assertThat(list.getFirst().getData(), is(2));
//                assertThat(list.getFirst().getNext().getData(), is(3));
//                assertThat(list.getLast().getData(), is(3));
//            }
//
//            @Test
//            public void preserves_correct_linking_order_after_removing_from_front() {
//                list.insertAtBack(2);
//                list.insertAtBack(3);
//
//                Object element = list.removeFromBack();
//
//                assertThat(element, is(3));
//                assertThat(list.getFirst().getData(), is(1));
//                assertThat(list.getFirst().getNext().getData(), is(2));
//                assertThat(list.getLast().getData(), is(2));
//            }
//
//        }
//
//        public static class WhenInsertingAtFrontAndThenAtBack extends SetUp {
//
//            @Override
//            void configure() {
//                list.insertAtFront(1);
//                list.insertAtBack(2);
//            }
//
//            @Test
//            public void first_element_is_the_one_inserted_at_front() {
//                assertThat(list.getFirst().getData(), is(1));
//            }
//
//            @Test
//            public void last_element_is_the_one_inserted_at_back() {
//                assertThat(list.getLast().getData(), is(2));
//            }
//
//            @Test
//            public void next_element_after_first_node_is_set_correctly() {
//                assertThat(list.getLast().getData(), is(2));
//            }
//
//            @Test
//            public void removeFromFront_removes_first_element() {
//
//                Object element = list.removeFromFront();
//
//                assertThat(element, is(1));
//                assertThat(list.getFirst().getData(), is(2));
//                assertThat(list.getFirst().getNext(), is(nullValue()));
//                assertThat(list.getLast().getData(), is(2));
//            }
//
//            @Test
//            public void removeFromBack_removes_last_element() {
//
//                Object element = list.removeFromBack();
//
//                assertThat(element, is(2));
//                assertThat(list.getFirst().getData(), is(1));
//                assertThat(list.getFirst().getNext(), is(nullValue()));
//                assertThat(list.getLast().getData(), is(1));
//            }
//
//            @Test
//            public void preserves_correct_linking_order_after_removing_from_back() {
//                list.insertAtBack(3);
//
//                Object element = list.removeFromFront();
//
//                assertThat(element, is(1));
//                assertThat(list.getFirst().getData(), is(2));
//                assertThat(list.getFirst().getNext().getData(), is(3));
//                assertThat(list.getLast().getData(), is(3));
//            }
//
//            @Test
//            public void preserves_correct_linking_order_after_removing_from_front() {
//                list.insertAtBack(3);
//
//                Object element = list.removeFromBack();
//
//                assertThat(element, is(3));
//                assertThat(list.getFirst().getData(), is(1));
//                assertThat(list.getFirst().getNext().getData(), is(2));
//                assertThat(list.getLast().getData(), is(2));
//            }
//        }
//
}