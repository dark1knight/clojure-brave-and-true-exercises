(ns practice.core
  (:gen-class))

;; a function to lazily generate triangular numbers
(defn gen-tri
  "Generates lazy sequence of triangular numbers"
  ([] (gen-tri 0 1)) ;; the default case
  ([sum n]
   (let [new-sum (+ sum n)]
     (cons new-sum (lazy-seq (gen-tri new-sum (inc n)))))))

;; test whether a number is triangular by taking numbers from a sequence until one that is
;; greater than or equal to.
(defn triangular?
  "Figure out of the number is triangular - ie. 1, 3, 6, 10, 15, ..."
  [n]
  (let [tri (gen-tri)]
    (= n (last (take-while #(>= n %) tri))))
  )

;; print numbers from 0 -> 10
(map println (range 11))
(defn to-ten
  ([]
   (to-ten 0))
  ([num]
   (println num)
   (if (< num 10)
   (recur (inc num)))))

(defn comp-two
  [f g]
  (fn [args]
    (f (apply g args))))

(defn chain-funcs
  [& funcs]
    (reduce (fn [f1 f2] (fn [arg] (f1 (f2 arg)))) funcs))

(defn my-comp
  ;; for a single function
  ([f]
   (fn [& args] (apply f args)))
  ;; for two functions
  ([f g]
   (fn [& args] (f (apply g args))))
  ;; n functions, n > 2
  ;; only need to call apply on the last one...
   )

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
