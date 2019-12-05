(ns aoc.day2-test
  (:require [clojure.test :refer :all]
            [aoc.day2 :as day2]))

(deftest executing-code
  (testing "Execute program"
    (are [x y] (= x y)
               [2 0 0 0 99] (day2/execute-code [1 0 0 0 99])
               [2 3 0 6 99] (day2/execute-code [2 3 0 3 99])
               [30, 1, 1, 4, 2, 5, 6, 0, 99] (day2/execute-code [1, 1, 1, 4, 99, 5, 6, 0, 99]))))
