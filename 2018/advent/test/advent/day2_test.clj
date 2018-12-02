(ns advent.day2-test
  (:require [advent.day2 :as day2]
            [clojure.test :refer [deftest is are testing]]))

(def test-input
  ["abcdef" ;; [0 0]
   "bababc" ;; [1 1]
   "abbcde" ;; [1 0]
   "abcccd" ;; [0 1]
   "aabcdd" ;; [1 0]
   "abcdee" ;; [1 0]
   "ababab" ;; [0 1]
   ])

(deftest first-part
  (is (= 12 (day2/first-part test-input)))
  (is (= 5434 (day2/first-part day2/input))))

(def test-input-part-two
  ["abcde"
   "fghij"
   "klmno"
   "pqrst"
   "fguij"
   "axcye"
   "wvxyz"])

(deftest second-part
  (is (= "fgij" (day2/second-part test-input-part-two)))
  (is (= "agimdjvlhedpsyoqfzuknpjwt" (day2/second-part day2/input))))
