算是设计模式的练习，加了个也不知算是工厂还是享元的东西，一开始初始化好固定的敌机数组，被击毁/越界的被回收然后可以重新入场；
相比原版的一直new和销毁越界/被击毁敌机对象，应该算优化了吧。