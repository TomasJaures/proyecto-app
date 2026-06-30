import { describe, it, expect, vi, beforeEach, afterEach } from "vitest";
import { renderHook, act } from "@testing-library/react";
import { useCountdown } from "../../hooks/useCountdown.js";

describe("useCountdown", () => {
  beforeEach(() => {
    vi.useFakeTimers();
  });

  afterEach(() => {
    vi.useRealTimers();
  });

  it("initializes with the given seconds", () => {
    const { result } = renderHook(() => useCountdown(120));
    expect(result.current.remaining).toBe(120);
    expect(result.current.minutes).toBe("02");
    expect(result.current.seconds).toBe("00");
    expect(result.current.isExpired).toBe(false);
  });

  it("counts down every second", () => {
    const { result } = renderHook(() => useCountdown(5));

    act(() => {
      vi.advanceTimersByTime(1000);
    });
    expect(result.current.remaining).toBe(4);

    act(() => {
      vi.advanceTimersByTime(2000);
    });
    expect(result.current.remaining).toBe(2);
  });

  it("marks as expired when it reaches zero", () => {
    const { result } = renderHook(() => useCountdown(2));

    act(() => {
      vi.advanceTimersByTime(2000);
    });
    expect(result.current.remaining).toBe(0);
    expect(result.current.isExpired).toBe(true);
  });

  it("formats minutes and seconds correctly", () => {
    const { result } = renderHook(() => useCountdown(185));
    expect(result.current.minutes).toBe("03");
    expect(result.current.seconds).toBe("05");
  });

  it("resets the countdown", () => {
    const { result } = renderHook(() => useCountdown(60));

    act(() => {
      vi.advanceTimersByTime(30000);
    });
    expect(result.current.remaining).toBe(30);

    act(() => {
      result.current.reset();
    });
    expect(result.current.remaining).toBe(60);
    expect(result.current.isExpired).toBe(false);
  });
});
