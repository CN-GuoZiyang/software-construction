package debug;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TopVotedCandidateTest {

	int[] persons = { 0, 1, 1, 0, 0, 1, 0 };
	int[] times = { 0, 5, 10, 15, 20, 25, 30 };

	TopVotedCandidate topVotedCandidate = new TopVotedCandidate(persons, times);

	@Test
	public void topVotedCandidateTest() {
		assertEquals(0, topVotedCandidate.q(3));
		assertEquals(1, topVotedCandidate.q(12));
		assertEquals(1, topVotedCandidate.q(25));
	}

}
