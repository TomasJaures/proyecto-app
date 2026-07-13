import { describe, it, expect } from "vitest";
import { renderHook, act } from "@testing-library/react";
import { usePendingChanges } from "../../hooks/usePendingChanges.js";

describe("usePendingChanges", () => {
  it("starts with no pending changes", () => {
    const { result } = renderHook(() => usePendingChanges([]));
    expect(result.current.hasPendingChanges).toBe(false);
    expect(result.current.saved).toEqual([]);
    expect(result.current.pending).toEqual([]);
  });

  it("detects pending changes when pending differs from saved", () => {
    const { result } = renderHook(() => usePendingChanges([]));

    act(() => {
      result.current.setPending(["new item"]);
    });

    expect(result.current.hasPendingChanges).toBe(true);
  });

  it("discards changes back to saved state", () => {
    const { result } = renderHook(() => usePendingChanges(["original"]));

    act(() => {
      result.current.setPending(["modified"]);
    });
    expect(result.current.hasPendingChanges).toBe(true);

    act(() => {
      result.current.discard();
    });
    expect(result.current.pending).toEqual(["original"]);
    expect(result.current.hasPendingChanges).toBe(false);
  });

  it("syncs saved state with new data", () => {
    const { result } = renderHook(() => usePendingChanges([]));

    act(() => {
      result.current.syncSaved(["synced data"]);
    });

    expect(result.current.saved).toEqual(["synced data"]);
    expect(result.current.pending).toEqual(["synced data"]);
    expect(result.current.hasPendingChanges).toBe(false);
  });
});
