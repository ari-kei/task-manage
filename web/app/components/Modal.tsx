import { useEffect, useRef } from "react";

interface ModalProps {
  isOpen: boolean;
  onClose: () => void;
  title: string;
  children: React.ReactNode;
}

export default function Modal({ isOpen, onClose, title, children }: ModalProps) {
  const modalRef = useRef<HTMLDivElement>(null);
  const firstFocusableElementRef = useRef<HTMLButtonElement | null>(null);
  const lastFocusableElementRef = useRef<HTMLButtonElement | null>(null);

  useEffect(() => {
    if (!isOpen) return;

    firstFocusableElementRef.current?.focus();

    const handleTab = (event: KeyboardEvent) => {

      const firstElement = modalRef.current?.querySelector('#close-modal-first-focus') as HTMLElement;
      const lastElement = modalRef.current?.querySelector('#modal-last-focus') as HTMLElement;

      if (event.shiftKey) { // Shift + Tab
        if (document.activeElement === firstElement) {
          event.preventDefault();
          lastElement.focus();
        }
      } else { // Tab
        if (document.activeElement === lastElement) {
          event.preventDefault();
          firstElement.focus();
        }
      }
    };

    document.addEventListener('keydown', handleTab);
    return () => {
      document.removeEventListener('keydown', handleTab);
    };
  }, [isOpen]);

  if (!isOpen) return null;

  return (
    <div ref={modalRef} className="relative z-10" aria-labelledby="modal-title" role="dialog" aria-modal="true">
      <div className="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity" aria-hidden="true"></div>

      <div className="fixed inset-0 z-10 w-screen overflow-y-auto">
        <div className="flex min-h-full items-end justify-center p-4 text-center sm:items-center sm:p-0">
          <div className="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all sm:my-8 sm:w-full sm:max-w-lg">
            <div className="flex items-center justify-between p-4 md:p-5 border-b rounded-t">
              <h3 className="text-xl font-semibold text-gray-900">{title}</h3>
              <button
                id="close-modal-first-focus"
                ref={firstFocusableElementRef}
                type="button"
                onClick={onClose}
                className="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-11 h-11 ms-auto inline-flex justify-center items-center"
                data-modal-hide="default-modal">
                <svg className="w-4 h-4" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                  viewBox="0 0 14 14">
                  <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2"
                    d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6" />
                </svg>
                <span className="sr-only">閉じる</span>
              </button>
            </div>
            {children}
            <button
              id="modal-last-focus"
              ref={lastFocusableElementRef}
              className="opacity-0"
            >最後のフォーカス可能な要素</button>
          </div>
        </div>
      </div>
    </div>
  );
}