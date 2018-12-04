(ns advent.day3-test
  (:require [advent.day3 :as day3]
            [clojure.test :refer [deftest is are testing]]))

'[Sample
  ........
  ...2222.
  ...2222.
  .11XX22.
  .11XX22.
  .111133.
  .111133.
  ........]

(def test-data
  ["#1 @ 1,3: 4x4"
   "#2 @ 3,1: 4x4"
   "#3 @ 5,5: 2x2"])

(deftest first-part-test
  (is (= 4 (day3/first-part test-data)))
  (is (= 100595 (day3/first-part day3/input))))
