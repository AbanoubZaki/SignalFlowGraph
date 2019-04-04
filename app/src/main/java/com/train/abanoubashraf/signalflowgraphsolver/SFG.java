package com.train.abanoubashraf.signalflowgraphsolver;


import java.util.ArrayList;
import java.util.List;

public class SFG {
    private AdjMatrixDirectGraph<String> graph = new AdjMatrixDirectGraph<>();
    private AllCyclesInDirectedGraphTarjan tarjan = new AllCyclesInDirectedGraphTarjan();

    private List<ArrayList<Vertex<String>>> individualLoops = new ArrayList<>();
    private List<Double> individualLoopGains = new ArrayList<>();

    private List<ArrayList<Vertex<String>>> forwardPaths = new ArrayList<>();
    private List<Double> forwardPathGains = new ArrayList<>();

    private List<List<ArrayList<Vertex<String>>>> combinationLoops = new ArrayList<>();

    private Vertex<String> input, output;

    private static SFG sfg = new SFG();

    private SFG () {
    }

    public static SFG getInstance(){
        return sfg;
    }

    public <T> AdjMatrixDirectGraph<String> getGraph () {
        return graph;
    }

    private double calculateGAin(ArrayList<Vertex<String>> path) {
        double gain = 1;
        for (int i = 0; i < path.size() - 1; i++) {
            gain *= graph.getEdgeGain(path.get(i), path.get(i+1));
        }
        return gain;
    }

    public List<ArrayList<Vertex<String>>> getIndividualLoops() {
        individualLoopGains.clear();
        individualLoops = tarjan.findAllSimpleCycles(graph);
        for (int i = 0; i < individualLoops.size(); i++) {
            individualLoopGains.add(calculateGAin(individualLoops.get(i)));
        }
        return individualLoops;
    }

    public void setInputOutPutVertices (Vertex<String> in, Vertex<String> out) {
        input = in;
        output = out;
    }

    public List<ArrayList<Vertex<String>>> getForwardPaths() {
        forwardPathGains.clear();
        /*
        find forward paths between 1st and last nodes
         */
        forwardPaths = graph.findpaths(input, output);

        for (ArrayList path : forwardPaths) {
            forwardPathGains.add(calculateGAin(path));
        }
        return forwardPaths;
    }

    public boolean areTouched (ArrayList<Vertex<String>> loop1, ArrayList<Vertex<String>> loop2) {
        if (loop1.size()*loop2.size() > 0 && loop1 != null && loop2 != null) {
            for (int i = 0; i < loop1.size(); i++) {
                if (loop2.contains(loop1.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    public void combinationUtil(List<ArrayList<Vertex<String>>> arr,
                                List<ArrayList<Vertex<String>>> data, int start, int end, int index, int r) {
        // Current combination is ready to be printed, print it
        if (index == r) {
            List<ArrayList<Vertex<String>>> l = new ArrayList<>();
            for (int j = 0; j < r; j++) {
                l.add(data.get(j));
            }
            combinationLoops.add(l);
            return;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i = start; i <= end && end - i + 1 >= r - index; i++) {
            data.add(index, arr.get(i));
//			data[index] = arr[i];
            combinationUtil(arr, data, i + 1, end, index + 1, r);
        }
    }

    public List<List<ArrayList<Vertex<String>>>> getCombinations(List<ArrayList<Vertex<String>>> arr) {
        // A temporary array to store all combination one by one
        int n = arr.size();
        for (int i = 2; i <= n; i++) {
            List<ArrayList<Vertex<String>>> data = new ArrayList<>(i);
            combinationUtil(arr, data, 0, n - 1, 0, i);
        }

        return combinationLoops;
    }

    public List<List<ArrayList<Vertex<String>>>> getNonTouchedLoops () {
        combinationLoops.clear();
        getCombinations(individualLoops);

        boolean touch = false;
        //indexes of combinations of loops that are touched to at the end.
        ArrayList<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < combinationLoops.size(); i++) {
            for (int j = 0; j < combinationLoops.get(i).size(); j++) {
                for (int k = j + 1; k < combinationLoops.get(i).size(); k++) {
                    if (areTouched(combinationLoops.get(i).get(j), combinationLoops.get(i).get(k))) {
                        indexes.add(i);
                        touch = true;
                        break;
                    }
                }
                if (touch) {
                    touch = false;
                    break;
                }
            }
        }

        for (int i = 0; i < indexes.size(); i++) {
            int k = indexes.get(i);
            combinationLoops.remove(k);
            for (int j = i + 1; j < indexes.size(); j++) {
                indexes.set(j, indexes.get(j) - 1);
            }
        }
        return combinationLoops;
    }

    public double getDelta () {
        double delta = 1;

        for (int i = 0; i < individualLoopGains.size(); i++) {
            delta -= individualLoopGains.get(i);
        }
        double m = 1;
        int e = -1;
        for (int i = 0; i < combinationLoops.size(); i++) {
            if (combinationLoops.get(i).size() % 2 == 1) {
                e = -1;
            } else {
                e = 1;
            }
            for (int j = 0; j < combinationLoops.get(i).size(); j++) {
                m *= calculateGAin(combinationLoops.get(i).get(j));
            }
            delta += e * m;
            m = 1;
        }
        return delta;
    }

    // check if a path is touched with any of loops in the list or not
    public boolean areLoopsTouchedWithPath (List<ArrayList<Vertex<String>>> loops
            , ArrayList<Vertex<String>> path) {
        for (int k = 0; k < loops.size(); k++) {
            if (areTouched(loops.get(k), path)) {
                return false;
            }
        }
        return true;
    }

    public double getNumerator () {
        double numerator = 0;
        double delta = 1,m = 1;
        int e = -1;

        for (int i = 0; i < forwardPaths.size(); i++) {
            for (int j = 0; j < individualLoopGains.size(); j++) {
                if (!areTouched(individualLoops.get(j), forwardPaths.get(i))) {
                    delta -= individualLoopGains.get(j);
                }
            }
            for (int j = 0; j < combinationLoops.size(); j++) {
                if (combinationLoops.get(j).size() % 2 == 1) {
                    e = -1;
                } else {
                    e = 1;
                }

                if (areLoopsTouchedWithPath(combinationLoops.get(j), forwardPaths.get(i))) {
                    for (int k = 0; k < combinationLoops.get(j).size(); k++) {
                        m *= calculateGAin(combinationLoops.get(i).get(j));
                    }
                    delta += e * m;
                    m = 1;
                }
            }
            numerator += delta * forwardPathGains.get(i);
            delta = 1;
            m = 1;
            e = -1;
        }
        return numerator;
    }

}