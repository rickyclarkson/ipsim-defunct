package ipsim.property

case class Property[T](private var t: T) { var beforeChanges: List[(T, T) => Unit] = Nil
                                           var afterChanges: List[T => Unit] = Nil
                                           def apply = t
                                           def update(t2: T) = { beforeChanges foreach (_(t, t2))
                                                                 t = t2
                                                                 afterChanges foreach (_(t2)) } }
