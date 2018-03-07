package search.analyzers;

import datastructures.concrete.ChainedHashSet;
import datastructures.concrete.KVPair;
import datastructures.concrete.dictionaries.ChainedHashDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;
import datastructures.interfaces.ISet;
import search.models.Webpage;

import java.net.URI;

/**
 * This class is responsible for computing the 'page rank' of all available
 * webpages. If a webpage has many different links to it, it should have a
 * higher page rank. See the spec for more details.
 */
public class PageRankAnalyzer {
	private IDictionary<URI, Double> pageRanks;

	/**
	 * Computes a graph representing the internet and computes the page rank of all
	 * available webpages.
	 *
	 * @param webpages
	 *            A set of all webpages we have parsed.
	 * @param decay
	 *            Represents the "decay" factor when computing page rank (see spec).
	 * @param epsilon
	 *            When the difference in page ranks is less then or equal to this
	 *            number, stop iterating.
	 * @param limit
	 *            The maximum number of iterations we spend computing page rank.
	 *            This value is meant as a safety valve to prevent us from infinite
	 *            looping in case our page rank never converges.
	 */
	public PageRankAnalyzer(ISet<Webpage> webpages, double decay, double epsilon, int limit) {
		// Implementation note: We have commented these method calls out so your
		// search engine doesn't immediately crash when you try running it for the
		// first time.
		//
		// You should uncomment these lines when you're ready to begin working
		// on this class.

		// Step 1: Make a graph representing the 'internet'
		IDictionary<URI, ISet<URI>> graph = this.makeGraph(webpages);

		// Step 2: Use this graph to compute the page rank for each webpage
		this.pageRanks = this.makePageRanks(graph, decay, limit, epsilon);

		// Note: we don't store the graph as a field: once we've computed the
		// page ranks, we no longer need it!
	}

	/**
	 * This method converts a set of webpages into an unweighted, directed graph, in
	 * adjacency list form.
	 *
	 * You may assume that each webpage can be uniquely identified by its URI.
	 *
	 * Note that a webpage may contain links to other webpages that are *not*
	 * included within set of webpages you were given. You should omit these links
	 * from your graph: we want the final graph we build to be entirely
	 * "self-contained".
	 */
	private IDictionary<URI, ISet<URI>> makeGraph(ISet<Webpage> webpages) {
		IDictionary<URI, ISet<URI>> graph = new ChainedHashDictionary<>();
		ISet<URI> exist = new ChainedHashSet<>();
		for (Webpage page : webpages) {
			exist.add(page.getUri());
		}
		for (Webpage page : webpages) {
			ISet<URI> singleWeb = new ChainedHashSet<>();
			IList<URI> links = page.getLinks();
			for (URI link : links) {
				if (exist.contains(link) && !link.equals(page.getUri())) {
					singleWeb.add(link);
				}
			}
			graph.put(page.getUri(), singleWeb);
		}
		return graph;
	}

	/**
	 * Computes the page ranks for all webpages in the graph.
	 *
	 * Precondition: assumes 'this.graphs' has previously been initialized.
	 *
	 * @param decay
	 *            Represents the "decay" factor when computing page rank (see spec).
	 * @param epsilon
	 *            When the difference in page ranks is less then or equal to this
	 *            number, stop iterating.
	 * @param limit
	 *            The maximum number of iterations we spend computing page rank.
	 *            This value is meant as a safety valve to prevent us from infinite
	 *            looping in case our page rank never converges.
	 */
	private IDictionary<URI, Double> makePageRanks(IDictionary<URI, ISet<URI>> graph, double decay, int limit,
			double epsilon) {
		// Step 1: The initialize step should go here
		IDictionary<URI, Double> rank = new ChainedHashDictionary<>();
		double initial = 1.0 / graph.size();
		for (KVPair<URI, ISet<URI>> pair : graph) {
			rank.put(pair.getKey(), initial);
		}
		for (int i = 0; i < limit; i++) {
			// so that new one does not overwrite old one before calculating related links
			IDictionary<URI, Double> temp = new ChainedHashDictionary<>();

			// Step 2: The update step should go here
			for (KVPair<URI, ISet<URI>> pair : graph) {
				// web page has zero out-going links
				if (pair.getValue().isEmpty()) {
					double update = decay * rank.get(pair.getKey()) / graph.size();
					for (KVPair<URI, Double> newRank : rank) {
						if (!temp.containsKey(newRank.getKey())) {
							temp.put(newRank.getKey(), update);
						} else {
							double old = temp.get(newRank.getKey());
							temp.put(newRank.getKey(), old + update);
						}
					}
				} else { 
					// has out-going links
					// calculating page A
					// iterating over the set to get related links on page A
					ISet<URI> info = pair.getValue();
					int denominator = info.size();
					double oldScore = rank.get(pair.getKey());
					for (URI link : info) { // B, C, D
						// get the number of that page's out-going links
						// out going links of page A
						if (temp.containsKey(link)) {
							temp.put(link, temp.get(link) + decay * oldScore / denominator);
						} else {
							temp.put(link, decay * oldScore / denominator);
						}
					}
				}
				if (!temp.containsKey(pair.getKey())) {
					temp.put(pair.getKey(), (1 - decay) / graph.size());
				} else {
					temp.put(pair.getKey(), temp.get(pair.getKey()) + (1 - decay) / graph.size());
				}
			}

			// Step 3: the convergence step should go here.
			// Return early if we've converged.
			boolean converge = true;
			for (KVPair<URI, Double> u : temp) {
				if (Math.abs(rank.get(u.getKey()) - u.getValue()) >= epsilon) {
					converge = false;
				}
			}
			if (converge) {
				return temp;
			} else {
				// finally, update temp into rank
				rank = temp;
			}
		}
		return rank;
	}

	/**
	 * Returns the page rank of the given URI.
	 *
	 * Precondition: the given uri must have been one of the uris within the list of
	 * webpages given to the constructor.
	 */
	public double computePageRank(URI pageUri) {
		// Implementation note: this method should be very simple: just one line!
		return pageRanks.get(pageUri);
	}
}
