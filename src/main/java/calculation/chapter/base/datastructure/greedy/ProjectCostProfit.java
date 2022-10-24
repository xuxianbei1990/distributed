package calculation.chapter.base.datastructure.greedy;

import calculation.chapter.three.ChooseSort;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 题6
 * 输入：正整数组costs， 正整数组profits，正数K，正数M
 * cost[i]表示i号项目的花费,
 * profit[i]表示i号项目在扣除花费之后还能正道的钱（利润）
 * K表示你只能串行的最多K个项目
 * M表示你初始的资金
 * 说明：每做完一个项目，马上获取的收益，可以支持你去做下一个项目。不能并行的做项目。
 * 输出：你最后获得的最大钱数。
 *
 * @author: xuxianbei
 * Date: 2021/12/2
 * Time: 14:54
 * Version:V1.0
 */
public class ProjectCostProfit {

    static class CostProfit {
        public Integer cost;
        public Integer profit;
        public Integer id;
    }

    public void execute(int[] costs, int[] profits, int maxProjectNum, int initMoney) {
        List<CostProfit> costProfitList = new ArrayList();
        for (int i = 0; i < costs.length; i++) {
            CostProfit costProfit = new CostProfit();
            costProfit.cost = costs[i];
            costProfit.profit = profits[i];
            costProfit.id = i;
            costProfitList.add(costProfit);
        }
        ChooseSort.practice(costProfitList, (a, b) -> a.cost > b.cost ? 1 : 0);

        int sumProject = 0;
        while (sumProject < maxProjectNum) {

            Stack<CostProfit> costStack = new Stack<>();
            for (CostProfit cost : costProfitList) {
                costStack.add(cost);
            }
            List<CostProfit> costList = new ArrayList<>();
            while (true) {
                CostProfit cost = costStack.peek();
                if (initMoney - cost.cost > 0) {
                    cost = costStack.pop();
                    costList.add(cost);
                } else {
                    break;
                }
            }
            ChooseSort.practice(costList, (a, b) -> a.profit > b.profit ? 0 : 1);
            Stack<CostProfit> profitStack = new Stack<>();
            for (CostProfit cost : costList) {
                profitStack.add(cost);
            }
            CostProfit profit = profitStack.pop();
            costProfitList.remove(profit);
            sumProject++;
            initMoney += profit.profit;
        }
        System.out.println(initMoney);
    }

    public static void main(String[] args) {
        ProjectCostProfit projectCostProfit = new ProjectCostProfit();
        projectCostProfit.execute(new int[]{1,2,3,30,12,14,5}, new int[]{2,4,5,7,1,6,3}, 4, 10);
    }
}
