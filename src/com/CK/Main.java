package com.CK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        int[] a = new int[]{1, 2};
        int[] b = new int[]{3, 5};
        int[] c = new int[]{6, 7};
        int[] d = new int[]{8, 10};
        int[] e = new int[]{12, 16};

        int[] newInt = new int[]{4, 8};
        int[][] intervals = new int[5][2];
        intervals[0] = a;
        intervals[1] = b;
        intervals[2] = c;
        intervals[3] = d;
        intervals[4] = e;

        Solution solution = new Solution();
        System.out.println(Arrays.deepToString(solution.insert(intervals, newInt)));

    }
}

class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        if (intervals.length == 0 || intervals[0].length == 0){
            int[][] newInt = new int[1][2];
            newInt[0] = newInterval;
            return newInt;
        }
        int left = 0, right = intervals.length - 1, mid = 0, midVal = 0, newIntervalStart = newInterval[0], newIntervalEnd = newInterval[1];
        List<int[]> intervalList = new ArrayList<>(Arrays.asList(intervals));
        while (left + 1 < right) {
            mid = (left + right) / 2;
            midVal = intervalList.get(mid)[0];
            if (midVal <= newIntervalStart) left = mid;
            else right = mid;
        }
        if (intervalList.get(right)[1] < newIntervalStart) left = right;
        int sPt = left, ePt = left, replacedStart, replacedEnd;
        // If not overlapped
        if (ePt == intervalList.size() - 1 && intervalList.get(ePt)[1] < newIntervalStart) {
            intervalList.add(new int[]{newIntervalStart, newIntervalEnd});
        } else if (sPt == 0 && intervalList.get(0)[0] > newIntervalEnd) {
            intervalList.add(0, new int[]{newIntervalStart, newIntervalEnd});
        } else if (intervalList.get(ePt)[1] < newIntervalStart && ePt != intervalList.size() - 1 && newIntervalEnd < intervalList.get(sPt + 1)[0]) {
            intervalList.add(sPt + 1, new int[]{newIntervalStart, newIntervalEnd});
        }

        // If overlapped
        else {
            if (intervalList.get(ePt)[1] >= newIntervalStart) {
                replaceOverlappedInterval(intervalList, sPt, ePt, newIntervalStart, newIntervalEnd);
            } else {
                replaceOverlappedInterval(intervalList, sPt + 1, ePt + 1, newIntervalStart, newIntervalEnd);
            }
        }
        int[][] res = new int[intervalList.size()][2];
        for (int i = 0; i < intervalList.size(); i++) {
            res[i] = intervalList.get(i);
        }

        return res;
    }

    private void replaceOverlappedInterval(List<int[]> intervalList, int sPt, int ePt, int newIntervalStart, int newIntervalEnd) {
        while (true) {
            if (ePt == intervalList.size() - 1) break;
            else if (intervalList.get(ePt + 1)[0] > newIntervalEnd)  break;
            ePt++;
        }
        int replacedStart = Math.min(newIntervalStart, intervalList.get(sPt)[0]);
        int replacedEnd = Math.max(intervalList.get(ePt)[1], newIntervalEnd);
        for (int i = sPt; i <= ePt; i++) {
            intervalList.remove(sPt);
        }
        intervalList.add(sPt, new int[]{replacedStart, replacedEnd});
    }
}

