import { describe, it, expect, vi } from "vitest";
import { render, screen, fireEvent } from "@testing-library/react";
import Modal from "../../components/Modal.jsx";

describe("Modal", () => {
  it("renders nothing when open is false", () => {
    const { container } = render(
      <Modal open={false} onClose={() => {}} title="Test">
        <p>Content</p>
      </Modal>
    );
    expect(container.innerHTML).toBe("");
  });

  it("renders title and children when open", () => {
    render(
      <Modal open={true} onClose={() => {}} title="My Modal">
        <p>Modal body</p>
      </Modal>
    );
    expect(screen.getByText("My Modal")).toBeInTheDocument();
    expect(screen.getByText("Modal body")).toBeInTheDocument();
  });

  it("calls onClose when overlay is clicked", () => {
    const onClose = vi.fn();
    render(
      <Modal open={true} onClose={onClose} title="Test">
        <p>Content</p>
      </Modal>
    );
    fireEvent.click(screen.getByText("Content").closest(".modal-overlay"));
    expect(onClose).toHaveBeenCalledOnce();
  });

  it("does not call onClose when modal box is clicked", () => {
    const onClose = vi.fn();
    render(
      <Modal open={true} onClose={onClose} title="Test">
        <p>Content</p>
      </Modal>
    );
    fireEvent.click(screen.getByText("Content").closest(".modal-caja"));
    expect(onClose).not.toHaveBeenCalled();
  });

  it("calls onClose when close button is clicked", () => {
    const onClose = vi.fn();
    render(
      <Modal open={true} onClose={onClose} title="Test">
        <p>Content</p>
      </Modal>
    );
    fireEvent.click(screen.getByLabelText("Cerrar modal"));
    expect(onClose).toHaveBeenCalledOnce();
  });
});
