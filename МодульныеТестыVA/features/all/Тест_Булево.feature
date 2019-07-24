# language: ru

@tree
@classname=ModuleExceptionPath

Функционал: Тест: Булево
	Как Разработчик
	Я Хочу чтобы возвращаемое значение метода совпадало с эталонным
	Чтобы я мог гарантировать работоспособность метода

@OnServer
Сценарий: Тест_Булево (сервер): ПроверитьИстинуНаИстину
	И я выполняю код встроенного языка на сервере
	| 'Тест_Булево.ПроверитьИстинуНаИстину(Контекст());' |

@OnServer
Сценарий: Тест_Булево (сервер): ПроверитьИстинуНаЛожь
	И я выполняю код встроенного языка на сервере
	| 'Тест_Булево.ПроверитьИстинуНаЛожь(Контекст());' |

@OnServer
Сценарий: Тест_Булево (сервер): ПроверитьЛожьНаИстину
	И я выполняю код встроенного языка на сервере
	| 'Тест_Булево.ПроверитьЛожьНаИстину(Контекст());' |

@OnServer
Сценарий: Тест_Булево (сервер): ПроверитьЛожьНаЛожь
	И я выполняю код встроенного языка на сервере
	| 'Тест_Булево.ПроверитьЛожьНаЛожь(Контекст());' |

Сценарий: Тест_Булево (клиент): ПроверитьИстинуНаИстину
	И я выполняю код встроенного языка
	| 'Тест_Булево.ПроверитьИстинуНаИстину(Контекст());' |

Сценарий: Тест_Булево (клиент): ПроверитьИстинуНаЛожь
	И я выполняю код встроенного языка
	| 'Тест_Булево.ПроверитьИстинуНаЛожь(Контекст());' |

Сценарий: Тест_Булево (клиент): ПроверитьЛожьНаИстину
	И я выполняю код встроенного языка
	| 'Тест_Булево.ПроверитьЛожьНаИстину(Контекст());' |

Сценарий: Тест_Булево (клиент): ПроверитьЛожьНаЛожь
	И я выполняю код встроенного языка
	| 'Тест_Булево.ПроверитьЛожьНаЛожь(Контекст());' |