import { describe, it, expect, beforeEach } from "vitest";
import {
  getStoredUser,
  storeUser,
  clearUser,
  isAuthenticated,
} from "../../services/authService.js";

describe("authService", () => {
  beforeEach(() => {
    localStorage.clear();
  });

  describe("getStoredUser", () => {
    it("returns null when no user is stored", () => {
      expect(getStoredUser()).toBeNull();
    });

    it("returns the stored user object", () => {
      const user = { name: "Test", role: "student" };
      localStorage.setItem("user", JSON.stringify(user));
      expect(getStoredUser()).toEqual(user);
    });

    it("returns null for invalid JSON", () => {
      localStorage.setItem("user", "invalid-json");
      expect(getStoredUser()).toBeNull();
    });
  });

  describe("storeUser", () => {
    it("stores user in localStorage", () => {
      const user = { name: "Ana", role: "docente" };
      storeUser(user);
      expect(JSON.parse(localStorage.getItem("user"))).toEqual(user);
    });
  });

  describe("clearUser", () => {
    it("removes user from localStorage", () => {
      localStorage.setItem("user", JSON.stringify({ name: "Test" }));
      clearUser();
      expect(localStorage.getItem("user")).toBeNull();
    });
  });

  describe("isAuthenticated", () => {
    it("returns false when no user stored", () => {
      expect(isAuthenticated()).toBe(false);
    });

    it("returns true when user exists", () => {
      localStorage.setItem("user", JSON.stringify({ name: "Test" }));
      expect(isAuthenticated()).toBe(true);
    });
  });
});
