(ns advent.day5-test
  (:require [advent.day5 :as day5]
            [clojure.test :refer [deftest is are testing]]))

(deftest from-description
  (is (= 0 (day5/first-part "aA")))
  (is (= 0 (day5/first-part "abBA")))
  (is (= 4 (day5/first-part "abAB")))
  (is (= 5 (day5/first-part "aabAAB")))
  (is (= (count "dabCBAcaDA") (day5/first-part "dabAcCaCBAcCcaDA"))))

(deftest part-one
  (is (= 10584 (day5/first-part day5/input))))

(comment
  (deftest part-two
    (is (= 6968 (day5/second-part day5/input))))
  )
