# language: ru

@tree
@classname=ModuleExceptionPath

Функционал: МодульныеТестыVA.Тест_Покрытие
	Как Разработчик
	Я Хочу чтобы возвращаемое значение метода совпадало с эталонным
	Чтобы я мог гарантировать работоспособность метода

@OnServer
Сценарий: ПроверитьПокрытие
	И я выполняю код встроенного языка на сервере
	| 'Тест_Покрытие.ПроверитьПокрытие(Контекст());' |