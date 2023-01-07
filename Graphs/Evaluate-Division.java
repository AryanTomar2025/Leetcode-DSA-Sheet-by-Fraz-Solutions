class Solution {

    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, Map<String, Double>> graph = Graph(equations, values);

        double[] answers = new double[queries.size()];
        int i = 0;
        for(List<String> query : queries){
            Set<String> visited = new HashSet<String>();
            if(query.get(0).equals(query.get(1))){
                if(graph.containsKey(query.get(0))){
                    answers[i] = 1.0;
                    i++;

                } else{
                    answers[i] = -1.0;
                    i++;

                }
                continue;
            }
            double answer = dfs(query.get(0), query.get(1), graph, visited);
            answers[i] = answer;
            i++;
        }

        return answers;
    }
    private double dfs(String start, String end,  Map<String, Map<String, Double>> graph, Set<String> visited) {
        if(!graph.containsKey(start)) {
            return -1.0;
        }

        if(graph.get(start).containsKey(end)) {
            return graph.get(start).get(end);
        }

        visited.add(start);
        Map<String, Double> Map = graph.get(start);
        for(Map.Entry<String, Double> entry: Map.entrySet()) {
            if(!visited.contains(entry.getKey())){

                double Value = dfs(entry.getKey(), end, graph, visited);

                if(Value!=-1){
                    return Value * entry.getValue();
                }
            }
        }
        return -1.0;

    }
    private Map<String, Map<String, Double>> Graph(List<List<String>> equations, double[] values) {
        
        Map<String, Map<String, Double>> graph = new HashMap<>();
        int i = 0;
        for(List<String> equation : equations) {
            String startPoint = equation.get(0);
            String endPoint = equation.get(1);
            double val = values[i];
            i++;
            graph.putIfAbsent(startPoint, new HashMap<>());

            graph.get(startPoint).put(endPoint, val);


            graph.putIfAbsent(endPoint, new HashMap<>());

            graph.get(endPoint).put(startPoint, 1/val);
        }
        return graph;

    }
}
