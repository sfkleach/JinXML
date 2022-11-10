package com.steelypip.powerups.jinxml.stdmodel;

import com.steelypip.powerups.jinxml.Element;
import com.steelypip.powerups.jinxml.Path;

public class SecondaryPath implements Path {

    Path _original;

    public SecondaryPath( Path _original ) {
        this._original = _original;
    }

    @Override
    public Element getElement() {
        return _original.getElement();
    }

    @Override
    public String getSelector() {
        return _original.getSelector();
    }

    @Override
    public int getPosition() {
        return _original.getPosition();
    }

    @Override
    public Path getParent() {
        return _original.getParent();
    }

    @Override
    public boolean isRoot() {
        return _original.isRoot();
    }

    @Override
    public Iterable<Path> generatePaths() {
        return _original.generatePaths();
    }

    @Override
    public Iterable<Path> generatePaths(String selector) {
        return _original.generatePaths(selector);
    }

    @Override
    public boolean isAlreadyGenerated() {
        return true;
    }

    @Override
    public boolean isOwnAncestor() {
        return _original.isOwnAncestor();
    }

}
