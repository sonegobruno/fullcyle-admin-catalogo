package com.fullcycle.admin.catalogo.application;

public abstract class UnitUseCase<INPUT> {
    public abstract void execute(INPUT input);
}
