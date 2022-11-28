package isep.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MergeSort<E> {
  public List<E> sort(List<E> original, Comparator<E> ce) {
    if (original == null || original.isEmpty() || original.size() == 1 || ce == null)
      return original;

    // divide the array into two halves
    int mid = original.size() / 2;

    List<E> left = original.subList(0, mid);
    List<E> right = original.subList(mid, original.size());

    // sort each half
    left = sort(left, ce);
    right = sort(right, ce);

    // merge the sorted halves
    return merge(left, right, ce);
  }

  private List<E> merge(List<E> left, List<E> right, Comparator<E> cmp) {
    // merging the 2 sub arrays into a single array
    List<E> result = new ArrayList<>();

    int i = 0, j = 0;

    // reorder the greater integers into the correct position
    while (i < left.size() && j < right.size()) {
      if (cmp.compare(left.get(i), right.get(j)) < 0) {
        result.add(left.get(i));
        i++;
      } else {
        result.add(right.get(j));
        j++;
      }
    }

    // finally, fill with the remainding integers
    while (i < left.size()) {
      result.add(left.get(i));
      i++;
    }
    while (j < right.size()) {
      result.add(right.get(j));
      j++;
    }

    return result;
  }
}
