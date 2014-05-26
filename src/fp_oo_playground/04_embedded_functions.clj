(ns fp-oo-playground.04-embedded-functions)

(def make
  (fn [type & args]
    (apply type args)))

(def send-to
  (fn [object message & args]
    (apply (message (:__methods__ object)) object args)))

(def Point
  (fn [x y]
    {:x x
     :y y
     :__class_symbol__ 'Point
     :__methods__ {
                   :class :__class_symbol__
                   :x :x
                   :y :y
                   :shift (fn [this x-inc y-inc]
                            (make Point (+ (:x this) x-inc)
                                        (+ (:y this) y-inc)))
                   :add (fn [this that]
                          (send-to this
                                   :shift
                                   (send-to that :x) (send-to that :y)))}}))

(def point-a (make Point 10 20))
(def point-b (make Point 50 30))

(send-to point-a :x)
(send-to point-a :y)
(send-to point-a :shift 100 1000)
(send-to point-a :add point-b)
