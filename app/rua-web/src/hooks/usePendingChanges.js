import { useState, useCallback } from "react";

export function usePendingChanges(initialState) {
  const [saved, setSaved] = useState(initialState);
  const [pending, setPending] = useState(initialState);
  const [isSaving, setIsSaving] = useState(false);

  const hasPendingChanges = JSON.stringify(saved) !== JSON.stringify(pending);

  const syncSaved = useCallback((data) => {
    setSaved(data);
    setPending(data);
  }, []);

  const discard = useCallback(() => {
    setPending(saved);
  }, [saved]);

  return {
    saved,
    pending,
    setPending,
    hasPendingChanges,
    isSaving,
    setIsSaving,
    syncSaved,
    discard,
  };
}
