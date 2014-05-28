(ns fp-oo-playground.05-class)

(def explicit-3-step-object-creation-and-initialization-make
  (fn [class & args]
    (let [allocated   {}
          seeded      (assoc allocated :__class_symbol__ (:__own_symbol__ class))
          constructor (:add-instance-values (:__instance_methods__ class))]
      (apply constructor seeded args))))

(def Point
  {
   :__own_symbol__ 'Point
   :__instance_methods__
   {
    :class :__class_symbol__

    :add-instance-values (fn [this x y]
                           (assoc this :x x :y y))

    :shift (fn [this x-inc y-inc]
             (make Point (+ (:x this) x-inc)
                         (+ (:y this) y-inc)))
    }
   })

;; Helpers
(def method-from-message
  (fn [message class]
    (message (:__instance_methods__ class))))

(def class-from-instance
  (fn [instance]
    (eval (:__class_symbol__ instance))))

(def apply-message-to
  (fn [class instance message args]
    (apply (method-from-message message class) instance args)))

(def a-point (make Point 5 5))
(apply-message-to Point a-point :shift [1 333])

;; Refactor version (using apply-message-to)
(def make
  (fn [class & args]
    (let [seeded {:__class_symbol__ (:__own_symbol__ class)}]
      (apply-message-to class seeded :add-instance-values args))))

;; Refactor version (using method-from-message and class-from-instance)
(def send-to
  (fn [instance message & args]
    (apply-message-to (class-from-instance instance) instance message args)))

(send-to (make Point 1 2) :shift -2 -3)
