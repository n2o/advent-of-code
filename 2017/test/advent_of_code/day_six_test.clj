(ns advent-of-code.day-six-test
  (:require [advent-of-code.day-six :as ds]
            [clojure.test :refer [deftest is testing]]))

(def input [0 5 10 0 11 14 13 4 11 8 8 7 1 4 12 11])

(deftest part-one-test
  (testing
    (is (= 5 (second (ds/run [0 2 7 0]))))
    (is (= 7864 (second (ds/run input))))))

(deftest part-two-test
  (is (= 1695 (-> input ds/run first ds/run second))))
