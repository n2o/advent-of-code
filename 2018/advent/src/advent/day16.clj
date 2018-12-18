(ns advent.day16
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.set :as set]))

(def input
  (->> "day16_first"
       io/resource
       slurp
       string/split-lines))

(defn convert-instruction [four-input]
  (let [[i1 i2 i3] (drop-last four-input)
        before (read-string (re-find #"\[.*\]" i1))
        output (map read-string (string/split (re-find #"\d+.\d.\d.\d" i2) #" "))
        after (read-string (re-find #"\[.*\]" i3))]
    {:before before
     :after after
     :opcode (first output)
     :A (second output)
     :B (nth output 2)
     :C (last output)}))

(defn parse-input [input]
  (let [parts (partition 4 (conj input ""))]
    (map convert-instruction parts)))

(def parsed-input (parse-input input))


;; -----------------------------------------------------------------------------

(defn- make-instruction-register [f {:keys [A B C]} registers]
  (assoc registers C (f (nth registers A) (nth registers B))))

(defn- make-instruction-immediate [f {:keys [A B C]} registers]
  (assoc registers C (f (nth registers A) B)))


(defn addr [instruction registers]
  (make-instruction-register + instruction registers))
(defn addi [instruction registers]
  (make-instruction-immediate + instruction registers))

(defn mulr [instruction registers]
  (make-instruction-register * instruction registers))
(defn muli [instruction registers]
  (make-instruction-immediate * instruction registers))

(defn banr [instruction registers]
  (make-instruction-register bit-and instruction registers))
(defn bani [instruction registers]
  (make-instruction-immediate bit-and instruction registers))

(defn borr [instruction registers]
  (make-instruction-register bit-or instruction registers))
(defn bori [instruction registers]
  (make-instruction-immediate bit-or instruction registers))

(defn setr [{:keys [A C]} registers]
  (assoc registers C (nth registers A)))
(defn seti [{:keys [A C]} registers]
  (assoc registers C A))

(defn gtir [{:keys [A B C]} registers]
  (let [result (if (> A (nth registers B)) 1 0)]
    (assoc registers C result)))
(defn gtri [{:keys [A B C]} registers]
  (let [result (if (> (nth registers A) B) 1 0)]
    (assoc registers C result)))
(defn gtrr [{:keys [A B C]} registers]
  (let [result (if (> (nth registers A) (nth registers B)) 1 0)]
    (assoc registers C result)))

(defn eqir [{:keys [A B C]} registers]
  (let [result (if (= A (nth registers B)) 1 0)]
    (assoc registers C result)))
(defn eqri [{:keys [A B C]} registers]
  (let [result (if (= (nth registers A) B) 1 0)]
    (assoc registers C result)))
(defn eqrr [{:keys [A B C]} registers]
  (let [result (if (= (nth registers A) (nth registers B)) 1 0)]
    (assoc registers C result)))

(def fns #{addr addi mulr muli banr bani borr bori
           setr seti gtir gtri gtrr eqir eqri eqrr})

;; -----------------------------------------------------------------------------

(defn single-input-all-fns [single-input]
  (get
   (frequencies
    (map (fn [f]
           (= (:after single-input) (f single-input (:before single-input))))
         fns))
   true))

(defn first-part [input]
  (count
   (filter #(<= 3 %) (map single-input-all-fns parsed-input))))

(comment
  (first-part parsed-input)
  )

;; -----------------------------------------------------------------------------

(defn possible-opcode-fn-combo [single-input]
  (remove nil?
          (map (fn [f]
                 (when
                     (= (:after single-input) (f single-input (:before single-input)))
                   [(:opcode single-input) f]))
               fns)))

(defn input-to-opcode-set [parsed-input]
  (reduce (fn [acc [opcode f]]
            (assoc acc opcode (conj (get acc opcode #{}) f)))
          {}
          (mapcat possible-opcode-fn-combo parsed-input)))

(defn remove-seen-from-myset [myset seen]
  (into {}
        (for [[k v] myset]
          [k (set/difference v seen)])))

(defn opcode->fn [myset]
  (loop [results {}
         seen #{}
         myset myset]
    (let [[opcode fset] (first (filter (fn [[_ v]] (= (count v) 1)) myset))
          results' (assoc results opcode (first fset))
          seen' (conj seen (first fset))
          myset' (remove-seen-from-myset (dissoc myset opcode)
                                         seen')]
      (if (empty? myset')
        results
        (recur results' seen' myset')))))

(def operations
  (assoc
   (opcode->fn (input-to-opcode-set parsed-input))
   11 bani))

(defn line->data [line]
  (let [[opcode a b c] (map read-string (string/split line #" "))
        operation (get operations opcode)]
    [operation a b c]))

(def program
  (->> "day16_second"
       io/resource
       slurp
       string/split-lines
       (map line->data)))

(defn second-part [program]
  (loop [prog program
         registers [0 0 0 0 0]]
    (let [[f a b c] (first prog)
          registers' (f {:A a :B b :C c} registers)]
      (if (empty? (rest prog))
        (first registers')
        (recur (rest prog) registers')))))

(second-part program)
