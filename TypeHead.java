package HLD.TypeHeadSearch;

import java.util.*;

class TypeHead {
    Trie root;
    HashMap<String,Float> freq;

    TypeHead() {
        root = new Trie('/');
        freq=new HashMap<>();
    }


    class Trie {
        char ch;
        ArrayList<SearchTermFrequency> top_Five;
        Trie[] children;

        Trie(char ch) {
            this.ch = ch;
            top_Five = new ArrayList<>();

            children = new Trie[26];
        }
    }
    class SearchTermFrequency {
        String search_term;
        float frequency;

        SearchTermFrequency(String search_term, float frequency) {
            this.search_term = search_term;
            this.frequency = frequency;
        }
    }
    public void incrementSearchTermFrequency(String search_term, int increment){
        this.freq.put(search_term, this.freq.getOrDefault(search_term, 0F) + increment);
        Trie curr = this.root;
        boolean isFound=false;

        for (int i = 0; i < search_term.length(); i++) {
            int temp = search_term.charAt(i) - 'a';
            // curr=curr.children[temp];
            if (curr.children[temp] == null) {
                curr.children[temp] = new Trie(search_term.charAt(i));
            }
            curr = curr.children[temp];
            //Collections.sort(curr.children[temp].top_Five);
            for (SearchTermFrequency entry : curr.top_Five) {
                if (entry.search_term.equals(search_term)) {
                    entry.frequency = freq.get(search_term);
                    isFound = true;
                    break;
                }
            }
            // Trie tempNode=curr.children[temp];
            Collections.sort(curr.top_Five, Comparator.comparingDouble(
                    (SearchTermFrequency entry) -> entry.frequency
            ).thenComparing(entry -> entry.search_term));
            if (!isFound && (curr.top_Five.size() < 5
                    || freq.get(search_term) >= curr.top_Five.get(0).frequency)) {
                if (curr.top_Five.size() < 5) {
                    curr.top_Five.add(new SearchTermFrequency(search_term, freq.get(search_term)));
                } else if (freq.get(search_term) > curr.top_Five.get(0).frequency
                        || search_term.compareTo(curr.top_Five.get(0).search_term) > 0) {
                    curr.top_Five.set(0, new SearchTermFrequency(search_term, freq.get(search_term)));

                }

            }
            //Collections.sort()
            Collections.sort(curr.top_Five, Comparator.comparingDouble(
                    (SearchTermFrequency entry) -> entry.frequency
            ).thenComparing(entry -> entry.search_term));
        }

    }

    public String[] findTopXSuggestion(String queryPrefix, int X) {

            String[] ans = new String[X];
            Arrays.fill(ans, "");
            Trie curr1 = root;

        Trie curr;
        for (int i = 0; i < queryPrefix.length(); i++) {
                int temp = queryPrefix.charAt(i) - 'a';
                curr = curr1.children[temp];
                if (curr1 == null) {
                    return ans;
                }
            }

            ArrayList<SearchTermFrequency> top_Five = curr.top_Five;
            int size=curr.top_Five.size();

            int temp=0;
            for (int i=size-1;i>=size-X;i--){
                if(i>=0)
                    ans[temp]=top_Five.get(i).search_term;
                temp++;
            }

            Arrays.sort(ans);
            return ans;



    }

    public void dayPasses(int decayFactor){

    }
}