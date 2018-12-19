(ns advent.day19
  (:require [clojure.java.io :as io]
            [clojure.string :as string]))

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

(def input
  (->> "day19"
       io/resource
       slurp
       string/split-lines))

(def ip
  (read-string (re-find #"\d+" (first input))))

(defn parse-line [[operation a b c]]
  (let [f (resolve (symbol operation))]
    {:f f
     :A (read-string a)
     :B (read-string b)
     :C (read-string c)}))

(def program
  (->> (rest input)
       (map #(string/split % #" "))
       (map parse-line)))

(defn exec-program [program registers]
  (loop [registers registers]
    (let [pointer (nth registers ip)
          instruction (nth program pointer)
          registers' ((:f instruction) instruction registers)
          inc-ip-register (update registers' ip inc)]
      (if (> (nth inc-ip-register ip)
             (count program))
        inc-ip-register
        (recur inc-ip-register)))))

(defn first-part [program]
  (first (exec-program program [0 0 0 0 0 0])))

(defn second-part [program]
  (first (exec-program program [1 0 0 0 0 0])))


(comment
  (time
   (first-part program))

  (time
   (second-part program))
  )
