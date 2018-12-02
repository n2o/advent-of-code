(ns advent-of-code.day-eight
  (:require [clojure.string :as string]))

(def state (atom {:largest-value-ever-seen 0}))

(defn clojurify [line]
  (-> line
      (string/replace #"inc|dec|==|!=" {"inc" "+" "dec" "-" "==" "=" "!=" "not="})
      (string/split #" ")))

(defn- str->clj [s]
  (eval (read-string s)))

(defn- maybe-assign! [var]
  (if-let [value (get @state var)]
    value
    (do (swap! state assoc var 0) 0)))

(defn store-largest-value! []
  (swap! state update
         :largest-value-ever-seen
         (fn [old]
           (let [current (second (apply max-key val @state))]
             (if (< old current) current old)))))

(defn execute! [[var1 f n1 _ var2 cond-sym n2]]
  (let [_ (maybe-assign! var1)
        f (str->clj f)
        n1 (str->clj n1)
        cond-fn (str->clj cond-sym)
        n2 (str->clj n2)]
    (when (cond-fn (maybe-assign! var2) n2)
      (do (swap! state update var1 #(f % n1))
          (store-largest-value!)))))

(defn- compile! [program]
  (reset! state {:largest-value-ever-seen 0})
  (doseq [line (mapv string/trim (string/split program #"\n"))]
    (execute! (clojurify line))))

(defn largest-value [program]
  (compile! program)
  (second (apply max-key val (dissoc @state :largest-value-ever-seen))))

(defn largest-value-ever-seen [program]
  (compile! program)
  (:largest-value-ever-seen @state))
