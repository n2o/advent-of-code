(ns advent.day1-test
  (:require [advent.day1 :as day1]
            [clojure.test :refer [deftest is are testing]]))

(deftest part-one
  (is (= 439 (day1/first-part day1/numbers))))

(deftest part-two
  (are [x y] (= x y)
    0      (day1/first-doubled-frequency [1 -1])
    10     (day1/first-doubled-frequency [3 3 4 -2 -4])
    5      (day1/first-doubled-frequency [-6 3 8 5 -6])
    14     (day1/first-doubled-frequency [7 7 -2 -7 -4])
    124645 (day1/first-doubled-frequency day1/numbers)))
