import { useState, useEffect, useCallback } from "react";

export function useCountdown(initialSeconds) {
  const [remaining, setRemaining] = useState(initialSeconds);

  useEffect(() => {
    if (remaining <= 0) return;

    const timer = setInterval(() => {
      setRemaining((prev) => prev - 1);
    }, 1000);

    return () => clearInterval(timer);
  }, [remaining]);

  const reset = useCallback(() => {
    setRemaining(initialSeconds);
  }, [initialSeconds]);

  const minutes = String(Math.floor(remaining / 60)).padStart(2, "0");
  const seconds = String(remaining % 60).padStart(2, "0");

  return { remaining, minutes, seconds, reset, isExpired: remaining <= 0 };
}
