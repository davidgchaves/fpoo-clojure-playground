(ns fp-oo-playground.06-java)
(declare class-from-instance make send-to)

(def Anything
  {
   :__own_symbol__ 'Anything
   :__instance_methods__
   {
    :add-instance-values identity

    :class-name :__class_symbol__

    :class (fn [this] (class-from-instance this))
   }
  }
)

(def Point
  {
   :__own_symbol__ 'Point
   :__superclass_symbol__ 'Anything
   :__instance_methods__
   {
    :add-instance-values (fn [this x y]
                           (assoc this :x x :y y))

    :shift (fn [this x-inc y-inc]
             (make Point (+ (:x this) x-inc)
                         (+ (:y this) y-inc)))

    :add (fn [this that]
           (send-to this :shift (:x that))
                                (:y that))
   }
  }
)

;; Helpers
(def class-symbol-above
  (fn [class-symbol]
    (assert (symbol? class-symbol))
    (:__superclass_symbol__ (eval class-symbol))))

(def class-instance-methods
  (fn [class-symbol]
    (assert (symbol? class-symbol))
    (:__instance_methods__ (eval class-symbol))))

(def class-from-instance
  (fn [instance]
    (assert (map? instance))
    (eval (:__class_symbol__ instance))))

(def lineage-1
  (fn [class-symbol so-far]
    (if (nil? class-symbol)
      so-far
      (lineage-1 (class-symbol-above class-symbol)
                 (cons class-symbol so-far)))))

(def lineage
  (fn [class-symbol]
    (lineage-1 class-symbol [])))

(def method-cache
  (fn [class]
    (let [class-symbol (:__own_symbol__ class)
          method-maps  (map class-instance-methods (lineage class-symbol))]
      (apply merge method-maps))))

(def apply-message-to
  (fn [class instance message args]
    (apply (message (method-cache class)) instance args)))

(def make
  (fn [class & args]
    (let [seeded {:__class_symbol__ (:__own_symbol__ class)}]
      (apply-message-to class seeded :add-instance-values args))))

(def send-to
  (fn [instance message & args]
    (apply-message-to (class-from-instance instance) instance message args)))

;; factorial-1 using the first recursive pattern
(def factorial-1
  (fn [n]
    (if (or (= n 0) (= n 1))
      1
      (* n (factorial-1 (dec n))))))

(factorial-1 0)
(factorial-1 1)
(factorial-1 4)

;; factorial-2 using the second recursive pattern
(def factorial-2
  (fn [n so-far]
    (if (or (= n 0) (= n 1))
      so-far
      (factorial-2 (dec n) (* n so-far)))))

(factorial-2 0 1)
(factorial-2 1 1)
(factorial-2 4 1)

;; custom-reduce helper
(def custom-reduce
  (fn [combiner seq-of-something so-far]
    (if (empty? seq-of-something)
      so-far
      (custom-reduce combiner
                     (rest seq-of-something)
                     (combiner (first seq-of-something) so-far)))))

;; add-sequence refactored
(def add-sequence
  (fn [seq-of-numbers]
    (custom-reduce + seq-of-numbers 0)))

(add-sequence [5 50 10 5])

;; mult-sequence refactored
(def mult-sequence
  (fn [seq-of-numbers]
    (custom-reduce * seq-of-numbers 1)))

(mult-sequence [50 10 5])

;; Task: convert a sequence of keywords into a silly and a non-so silly map
;; silly-map
(def into-map-1
  (fn [a-keyword a-map]
    (assoc a-map a-keyword 0)))

(def into-map-2
  (fn [a-map a-keyword]
    (assoc a-map a-keyword 0)))

(custom-reduce into-map-1 [:a :b :c] {})

;; non-so-silly-map
(def into-map-with-position-1
  (fn [a-keyword a-map]
    (assoc a-map a-keyword (count a-map))))

(def into-map-with-position-2
  (fn [a-map a-keyword]
    (assoc a-map a-keyword (count a-map))))

(custom-reduce into-map-with-position-1 [:a :b :c] {})

;; reduce vs custom-reduce
(reduce        + 0 [5 50 10 5])
(custom-reduce + [5 50 10 5] 0)

(reduce        * 1 [50 10 5])
(custom-reduce * [50 10 5] 1)

(reduce        into-map-2 {} [:a :b :c])
(custom-reduce into-map-1 [:a :b :c] {})

(reduce        into-map-with-position-2 {} [:a :b :c])
(custom-reduce into-map-with-position-1 [:a :b :c] {})
