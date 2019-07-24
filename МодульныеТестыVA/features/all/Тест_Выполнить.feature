# language: ru

@tree
@classname=ModuleExceptionPath

Функционал: Тест: Выполнить
	Как Разработчик
	Я Хочу чтобы возвращаемое значение метода совпадало с эталонным
	Чтобы я мог гарантировать работоспособность метода

@OnServer
Сценарий: Тест_Выполнить (сервер): ПроверитьВыполнилосьНаИстину
	И я выполняю код встроенного языка на сервере
	| 'Тест_Выполнить.ПроверитьВыполнилосьНаИстину(Контекст());' |

@OnServer
Сценарий: Тест_Выполнить (сервер): ПроверитьВыполнилосьНаЛожь
	И я выполняю код встроенного языка на сервере
	| 'Тест_Выполнить.ПроверитьВыполнилосьНаЛожь(Контекст());' |

@OnServer
Сценарий: Тест_Выполнить (сервер): ПроверитьНеВыполнилосьНаИстину
	И я выполняю код встроенного языка на сервере
	| 'Тест_Выполнить.ПроверитьНеВыполнилосьНаИстину(Контекст());' |

@OnServer
Сценарий: Тест_Выполнить (сервер): ПроверитьНеВыполнилосьНаЛожь
	И я выполняю код встроенного языка на сервере
	| 'Тест_Выполнить.ПроверитьНеВыполнилосьНаЛожь(Контекст());' |

@OnServer
Сценарий: Тест_Выполнить (сервер): ПроверитьМетодВыполнилсяНаИстина
	И я выполняю код встроенного языка на сервере
	| 'Тест_Выполнить.ПроверитьМетодВыполнилсяНаИстина(Контекст());' |

@OnServer
Сценарий: Тест_Выполнить (сервер): ПроверитьМетодВыполнилсяНаЛожь
	И я выполняю код встроенного языка на сервере
	| 'Тест_Выполнить.ПроверитьМетодВыполнилсяНаЛожь(Контекст());' |

@OnServer
Сценарий: Тест_Выполнить (сервер): ПроверитьМетодНеВыполнилсяНаИстина
	И я выполняю код встроенного языка на сервере
	| 'Тест_Выполнить.ПроверитьМетодНеВыполнилсяНаИстина(Контекст());' |

@OnServer
Сценарий: Тест_Выполнить (сервер): ПроверитьМетодНеВыполнилсяНаЛожь
	И я выполняю код встроенного языка на сервере
	| 'Тест_Выполнить.ПроверитьМетодНеВыполнилсяНаЛожь(Контекст());' |

Сценарий: Тест_Выполнить (клиент): ПроверитьВыполнилосьНаИстину
	И я выполняю код встроенного языка
	| 'Тест_Выполнить.ПроверитьВыполнилосьНаИстину(Контекст());' |

Сценарий: Тест_Выполнить (клиент): ПроверитьВыполнилосьНаЛожь
	И я выполняю код встроенного языка
	| 'Тест_Выполнить.ПроверитьВыполнилосьНаЛожь(Контекст());' |

Сценарий: Тест_Выполнить (клиент): ПроверитьНеВыполнилосьНаИстину
	И я выполняю код встроенного языка
	| 'Тест_Выполнить.ПроверитьНеВыполнилосьНаИстину(Контекст());' |

Сценарий: Тест_Выполнить (клиент): ПроверитьНеВыполнилосьНаЛожь
	И я выполняю код встроенного языка
	| 'Тест_Выполнить.ПроверитьНеВыполнилосьНаЛожь(Контекст());' |

Сценарий: Тест_Выполнить (клиент): ПроверитьМетодВыполнилсяНаИстина
	И я выполняю код встроенного языка
	| 'Тест_Выполнить.ПроверитьМетодВыполнилсяНаИстина(Контекст());' |

Сценарий: Тест_Выполнить (клиент): ПроверитьМетодВыполнилсяНаЛожь
	И я выполняю код встроенного языка
	| 'Тест_Выполнить.ПроверитьМетодВыполнилсяНаЛожь(Контекст());' |

Сценарий: Тест_Выполнить (клиент): ПроверитьМетодНеВыполнилсяНаИстина
	И я выполняю код встроенного языка
	| 'Тест_Выполнить.ПроверитьМетодНеВыполнилсяНаИстина(Контекст());' |

Сценарий: Тест_Выполнить (клиент): ПроверитьМетодНеВыполнилсяНаЛожь
	И я выполняю код встроенного языка
	| 'Тест_Выполнить.ПроверитьМетодНеВыполнилсяНаЛожь(Контекст());' |