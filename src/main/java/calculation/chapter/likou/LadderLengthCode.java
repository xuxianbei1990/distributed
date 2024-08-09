package calculation.chapter.likou;

import java.util.*;

public class LadderLengthCode {

    /**
     * 想到最简单一个实现方式，一个函数判断该字符串是否在列表中找到。
     * 能找到则把该这个字符串放到列表中，
     * <p>
     * 找到所有的邻居
     *
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        wordList.add(beginWord);
        Map<String, List<String>> nextMap = getNexts(wordList);
        Map<String, Integer> distanceMap = getDistance(beginWord, nextMap);
        return distanceMap.get(endWord);
    }

    private Map<String, Integer> getDistance(String beginWord, Map<String, List<String>> nextMap) {
        Map<String, Integer> distances = new HashMap<>();
        distances.put(beginWord, 0);
        LinkedList<String> queue = new LinkedList<>();
        queue.add(beginWord);
        HashSet<String> set = new HashSet<>();
        set.add(beginWord);
        while (!queue.isEmpty()) {
            String cur = queue.poll();
            List<String> nexts = nextMap.get(cur);
            for (int i = 0; i < nexts.size(); i++) {
                if (!set.contains(nexts.get(i))) {
                    distances.put(nexts.get(i), distances.get(cur) + 1);
                    queue.add(nexts.get(i));
                    set.add(nexts.get(i));
                }
            }

        }
        return distances;
    }


    private Map<String, List<String>> getNexts(List<String> wordList) {
        Map<String, List<String>> result = new HashMap<>();
        String key;
        HashSet<String> set = new HashSet<>(wordList);
        for (int i = 0; i < wordList.size(); i++) {
            key = wordList.get(i);
            result.put(key, find(key.toCharArray(), set));
        }
        return result;
    }


    public List<String> find(char[] chars, HashSet<String> set) {
        List<String> ans = new ArrayList<>();
        for (int i = 0; i < chars.length; i++) {
            for (char a = 'a'; a <= 'z'; a++) {
                char b = chars[i];
                if (b != a) {
                    chars[i] = a;
                    if (set.contains(String.valueOf(chars))) {
                        ans.add(String.valueOf(chars));
                    }
                    chars[i] = b;
                }
            }
        }
        return ans;
    }

    void connect(char[][] board, int i, int j) {
        if (i < 0 || i == board.length || j < 0 || j == board[i].length || board[i][j] == 'X') {
            return;
        }
        board[i][j] = 'F';
        connect(board, i - 1, j);
        connect(board, i + 1, j);
        connect(board, i, j - 1);
        connect(board, i, j + 1);
    }

    public void solve(char[][] board) {
        for (int j = 0; j < board[0].length; j++) {
            if (board[0][j] == 'O') {
                connect(board, 0, j);
            }
            if (board[board.length - 1][j] == 'O') {
                connect(board, board.length - 1, j);
            }
        }

        for (int i = 0; i < board.length; i++) {
            if (board[i][0] == 'O') {
                connect(board, i, 0);
            }
            if (board[i][board[0].length - 1] == 'O') {
                connect(board, i, board[0].length - 1);
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 'O'){
                    board[i][j] = 'X';
                }
                if (board[i][j] == 'F'){
                    board[i][j] = 'O';
                }
            }
        }
    }

    public static void main(String[] args) {
        LadderLengthCode ladderLengthCode = new LadderLengthCode();
        System.out.println(ladderLengthCode.ladderLength("hit", "cog",
                new ArrayList<String>(Arrays.asList("hot", "dot", "dog", "lot", "log", "cog"))));
    }
}
