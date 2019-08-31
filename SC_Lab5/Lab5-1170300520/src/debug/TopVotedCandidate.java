package debug;
/**
 * In an election, the i-th vote was cast for persons[i] at time times[i].
 * <p>
 * Now, we would like to implement the following query function:
 * TopVotedCandidate.query(int t) will return the number of the person that was
 * leading the election at time t.
 * <p>
 * Votes cast at time t will count towards our query. In the case of a tie, the
 * most recent vote (among tied candidates) wins.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: ["TopVotedCandidate","query","query","query","query","query","query"],
 * [[[0,1,1,0,0,1,0],[0,5,10,15,20,25,30]],[3],[12],[25],[15],[24],[8]]
 * Output:
 * [null,0,1,1,0,0,1]
 * <p>
 * Explanation:
 * At time 3, the votes are [0], and 0 is leading.
 * At time 12, the votes are [0,1,1], and 1 is leading.
 * At time 25, the votes are [0,1,1,0,0,1], and 1 is leading (as ties go to the most recent
 * vote.)
 * This continues for 3 more queries at time 15, 24, and 8.
 * <p>
 * <p>
 * Note:
 * <p>
 * 1 <= persons.length = times.length <= 5000
 * 0 <= persons[i] <= persons.length
 * times is a strictly increasing array with all elements in [0, 10^9].
 * TopVotedCandidate.query is called at most 10000 times per test case.
 * TopVotedCandidate.query(int t) is always called with t >= times[0].
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TopVotedCandidate {
  List<List<Vote>> voteList;

  public TopVotedCandidate(int[] persons, int[] times) {
    voteList = new ArrayList<>();
    Map<Integer, Integer> count = new HashMap<>();
    for (int i = 0; i < persons.length; ++i) {
      int p = persons[i];
      int t = times[i];
      int c = count.getOrDefault(p, 0);

      count.put(p, c + 1);
      while (voteList.size() <= c) {
        voteList.add(new ArrayList<>());
      }
      voteList.get(c).add(new Vote(p, t));
    }
  }

  public int query(int t) {
    int lo = 1;
    int hi = voteList.size();
    while (lo < hi) {
      int mi = lo + (hi - lo) / 2;
      if (voteList.get(mi).get(0).time <= t) {
        lo = mi + 1;
      } else {
        hi = mi;
      }
    }
    int i = lo - 1;

    lo = 0;
    hi = voteList.get(i).size();
    while (lo < hi) {
      int mi = lo + (hi - lo) / 2;
      if (voteList.get(i).get(mi).time <= t) {
        lo = mi + 1;
      } else {
        hi = mi;
      }
    }
    int j = Math.max(lo - 1, 0);
    return voteList.get(i).get(j).person;
  }
}