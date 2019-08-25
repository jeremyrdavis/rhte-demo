package com.redhat.rhte.demos;

public enum Authors {

  SHAKESPEARE, HAMILTON, SWARZENEGGAR;

  @Override
  public String toString(){
    switch (this) {
      case SHAKESPEARE:
        return "Shakespeare";
      case HAMILTON:
        return "Hamilton";
      case SWARZENEGGAR:
        return "Swarzeneggar";
    }
    return "unknown";
  }
}
